package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.model.dto.request.cart.*;
import com.ecommerce.model.dto.response.cart.*;
import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(String sessionId) {
        Cart cart = findOrCreateCart(sessionId, null);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartByUser(Long userId) {
        Cart cart = findOrCreateCart(null, userId);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addToCart(String sessionId, Long userId, AddToCartRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));

        if (!product.isActive() || product.getIsDeleted()) {
            throw new BadRequestException("Product is not available");
        }

        Cart cart = findOrCreateCart(sessionId, userId);

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            cartItemRepository.save(item);
        } else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .unitPrice(product.getPrice())
                    .total(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                    .isActive(true)
                    .build();
            cartItemRepository.save(cartItem);
        }

        recalculateCart(cart);
        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(String sessionId, Long userId, UpdateCartItemRequest request) {
        Cart cart = findOrCreateCart(sessionId, userId);
        CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item", "id", request.getCartItemId()));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Cart item does not belong to this cart");
        }

        if (request.getQuantity() <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(request.getQuantity());
            cartItem.setTotal(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        recalculateCart(cart);
        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse removeFromCart(String sessionId, Long userId, Long cartItemId) {
        Cart cart = findOrCreateCart(sessionId, userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item", "id", cartItemId));

        cartItemRepository.delete(cartItem);
        recalculateCart(cart);
        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse clearCart(String sessionId, Long userId) {
        Cart cart = findOrCreateCart(sessionId, userId);
        cartItemRepository.deleteByCartIdAndIsActiveTrue(cart.getId());
        cart.setSubtotal(BigDecimal.ZERO);
        cart.setTaxAmount(BigDecimal.ZERO);
        cart.setTotal(BigDecimal.ZERO);
        cart.setItemCount(0);
        cart.setCoupon(null);
        cart.setDiscountAmount(BigDecimal.ZERO);
        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse applyCoupon(String sessionId, Long userId, String couponCode) {
        Cart cart = findOrCreateCart(sessionId, userId);

        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new CouponException("Invalid coupon code"));

        if (!coupon.isValid()) {
            throw new CouponException("Coupon is expired or fully used");
        }

        if (coupon.getMinimumOrderAmount() != null && cart.getSubtotal().compareTo(coupon.getMinimumOrderAmount()) < 0) {
            throw new CouponException("Minimum order amount of " + coupon.getMinimumOrderAmount() + " not met");
        }

        cart.setCoupon(coupon);
        recalculateCart(cart);
        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse removeCoupon(String sessionId, Long userId) {
        Cart cart = findOrCreateCart(sessionId, userId);
        cart.setCoupon(null);
        cart.setDiscountAmount(BigDecimal.ZERO);
        recalculateCart(cart);
        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse moveToWishlist(String sessionId, Long userId, Long cartItemId, Long wishlistId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Cart cart = findOrCreateCart(sessionId, userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item", "id", cartItemId));

        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist", "id", wishlistId));

        if (!wishlistItemRepository.existsByWishlistIdAndProductId(wishlistId, cartItem.getProduct().getId())) {
            WishlistItem wishlistItem = WishlistItem.builder()
                    .wishlist(wishlist)
                    .product(cartItem.getProduct())
                    .priceAtAdd(cartItem.getUnitPrice())
                    .build();
            wishlistItemRepository.save(wishlistItem);
        }

        cartItemRepository.delete(cartItem);
        recalculateCart(cart);
        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public Cart mergeCarts(String sessionId, Long userId) {
        Cart sessionCart = cartRepository.findBySessionIdAndIsActiveTrue(sessionId).orElse(null);
        Cart userCart = cartRepository.findByUserIdAndIsActiveTrue(userId).orElse(null);

        if (sessionCart == null) return userCart;
        if (userCart == null) {
            sessionCart.setUser(userRepository.getReferenceById(userId));
            return cartRepository.save(sessionCart);
        }

        for (CartItem sessionItem : cartItemRepository.findByCartIdAndIsActiveTrue(sessionCart.getId())) {
            Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(userCart.getId(), sessionItem.getProduct().getId());
            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + sessionItem.getQuantity());
                item.setTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                cartItemRepository.save(item);
            } else {
                sessionItem.setCart(userCart);
                cartItemRepository.save(sessionItem);
            }
        }

        sessionCart.setActive(false);
        cartRepository.save(sessionCart);

        recalculateCart(userCart);
        return cartRepository.save(userCart);
    }

    @Override
    @Transactional
    public void convertCartToOrder(Cart cart) {
        cart.setConverted(true);
        cart.setActive(false);
        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartSummary(String sessionId, Long userId) {
        return getCart(sessionId);
    }

    private Cart findOrCreateCart(String sessionId, Long userId) {
        if (userId != null) {
            return cartRepository.findByUserIdAndIsActiveTrue(userId)
                    .orElseGet(() -> {
                        User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                        Cart cart = Cart.builder().user(user).isActive(true).build();
                        return cartRepository.save(cart);
                    });
        }
        return cartRepository.findBySessionIdAndIsActiveTrue(sessionId)
                .orElseGet(() -> {
                    Cart cart = Cart.builder().sessionId(sessionId).isActive(true).build();
                    return cartRepository.save(cart);
                });
    }

    private void recalculateCart(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCartIdAndIsActiveTrue(cart.getId());
        BigDecimal subtotal = items.stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setSubtotal(subtotal);
        cart.setTaxAmount(BigDecimal.ZERO);
        cart.setItemCount(items.size());

        BigDecimal discount = BigDecimal.ZERO;
        if (cart.getCoupon() != null) {
            if (cart.getCoupon().getType() == com.ecommerce.model.enums.CouponType.PERCENTAGE) {
                discount = subtotal.multiply(cart.getCoupon().getValue()).divide(BigDecimal.valueOf(100));
            } else {
                discount = cart.getCoupon().getValue();
            }
            if (cart.getCoupon().getMaximumDiscountAmount() != null && discount.compareTo(cart.getCoupon().getMaximumDiscountAmount()) > 0) {
                discount = cart.getCoupon().getMaximumDiscountAmount();
            }
        }
        cart.setDiscountAmount(discount);
        cart.setTotal(subtotal.subtract(discount));
    }

    private CartResponse mapToCartResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cartItemRepository.findByCartIdAndIsActiveTrue(cart.getId())
                .stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());

        return CartResponse.builder()
                .id(cart.getId())
                .items(itemResponses)
                .itemCount(cart.getItemCount())
                .subtotal(cart.getSubtotal())
                .taxAmount(cart.getTaxAmount())
                .shippingEstimate(cart.getShippingEstimate())
                .discountAmount(cart.getDiscountAmount())
                .total(cart.getTotal())
                .currency(cart.getCurrency())
                .couponCode(cart.getCoupon() != null ? cart.getCoupon().getCode() : null)
                .build();
    }

    private CartItemResponse mapToCartItemResponse(CartItem item) {
        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .productSku(item.getProduct().getSku())
                .productImage(item.getProduct().getImages().isEmpty() ? null :
                        item.getProduct().getImages().iterator().next().getUrl())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .total(item.getTotal())
                .inStock(true)
                .isActive(item.isActive())
                .build();
    }
}

package com.ecommerce.facade;

import com.ecommerce.model.dto.request.order.CheckoutRequest;
import com.ecommerce.model.dto.response.order.OrderResponse;
import com.ecommerce.model.entity.*;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.repository.*;
import com.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutFacade {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final LoyaltyPointsRepository loyaltyPointsRepository;

    @Transactional
    public OrderResponse processCheckout(Long userId, CheckoutRequest request) {
        log.info("Processing checkout for user: {}", userId);

        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new com.ecommerce.exception.BadRequestException("Cart is empty"));

        validateCartStock(cart);

        reserveInventory(cart);

        try {
            OrderResponse order = orderService.createOrder(userId, request);

            deductInventory(cart);

            processLoyaltyPoints(userId, order.getTotal());

            notificationService.sendOrderNotification(userId, order.getOrderNumber(), "CONFIRMED");

            return order;
        } catch (Exception e) {
            releaseReservedInventory(cart);
            log.error("Checkout failed for user {}: {}", userId, e.getMessage());
            throw e;
        }
    }

    private void validateCartStock(Cart cart) {
        var items = cartItemRepository.findByCartIdAndIsActiveTrue(cart.getId());
        for (CartItem item : items) {
            int available = inventoryService.getAvailableStock(item.getProduct().getId());
            if (available < item.getQuantity()) {
                throw new com.ecommerce.exception.InsufficientStockException(
                        item.getProduct().getName(), item.getQuantity(), available);
            }
        }
    }

    private void reserveInventory(Cart cart) {
        var items = cartItemRepository.findByCartIdAndIsActiveTrue(cart.getId());
        for (CartItem item : items) {
            inventoryService.reserveStock(item.getProduct().getId(), null, item.getQuantity());
        }
    }

    private void deductInventory(Cart cart) {
        var items = cartItemRepository.findByCartIdAndIsActiveTrue(cart.getId());
        for (CartItem item : items) {
            inventoryService.fulfillStock(item.getProduct().getId(), null, item.getQuantity());
        }
    }

    private void releaseReservedInventory(Cart cart) {
        var items = cartItemRepository.findByCartIdAndIsActiveTrue(cart.getId());
        for (CartItem item : items) {
            inventoryService.releaseStock(item.getProduct().getId(), null, item.getQuantity());
        }
    }

    private void processLoyaltyPoints(Long userId, BigDecimal orderTotal) {
        try {
            var loyaltyPoints = loyaltyPointsRepository.findByUserId(userId).orElse(null);
            if (loyaltyPoints != null) {
                int pointsToEarn = orderTotal.intValue() / 10;
                loyaltyPoints.addPoints(pointsToEarn);
                loyaltyPointsRepository.save(loyaltyPoints);
            }
        } catch (Exception e) {
            log.error("Failed to process loyalty points for user {}: {}", userId, e.getMessage());
        }
    }
}

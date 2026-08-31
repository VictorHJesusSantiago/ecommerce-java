package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.model.dto.request.order.*;
import com.ecommerce.model.dto.response.order.*;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.entity.*;
import com.ecommerce.model.enums.OrderSource;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ShippingMethodRepository shippingMethodRepository;
    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(Long userId, CheckoutRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new BadRequestException("Cart is empty"));

        List<CartItem> cartItems = cartItemRepository.findByCartIdAndIsActiveTrue(cart.getId());
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        Address shippingAddress = addressRepository.findById(request.getShippingAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", request.getShippingAddressId()));

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .user(user)
                .status(OrderStatus.PENDING)
                .subtotal(cart.getSubtotal())
                .taxAmount(cart.getTaxAmount())
                .shippingAmount(BigDecimal.ZERO)
                .discountAmount(cart.getDiscountAmount())
                .total(cart.getTotal())
                .currency("USD")
                .shippingAddress(shippingAddress)
                .billingAddress(shippingAddress)
                .shippingMethod(shippingMethodRepository.findById(request.getShippingMethodId()).orElse(null))
                .coupon(cart.getCoupon())
                .paymentMethod(request.getPaymentMethod())
                .customerNote(request.getCustomerNote())
                .source(OrderSource.WEB)
                .sendEmailConfirmation(request.isSendEmailConfirmation())
                .build();

        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(cartItem.getProduct())
                    .productName(cartItem.getProduct().getName())
                    .productSku(cartItem.getProduct().getSku())
                    .unitPrice(cartItem.getUnitPrice())
                    .quantity(cartItem.getQuantity())
                    .total(cartItem.getTotal())
                    .isTaxable(cartItem.getProduct().isTaxable())
                    .requiresShipping(cartItem.getProduct().isRequiresShipping())
                    .build();
            orderItemRepository.save(orderItem);
        }

        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(savedOrder)
                .newStatus(OrderStatus.PENDING)
                .comment("Order placed")
                .isSystemGenerated(true)
                .build();
        savedOrder.getStatusHistory().add(history);
        orderRepository.save(savedOrder);

        convertCartToOrder(cart);
        log.info("Order created: {} for user {}", savedOrder.getOrderNumber(), userId);

        return mapToOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return mapToOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderbyNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderNumber", orderNumber));
        return mapToOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<OrderResponse> getUserOrders(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        return PaginatedResponse.of(
                orders.getContent().stream().map(this::mapToOrderResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), orders.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<OrderResponse> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return PaginatedResponse.of(
                orders.getContent().stream().map(this::mapToOrderResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), orders.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        Page<Order> orders = orderRepository.findByStatus(status, pageable);
        return PaginatedResponse.of(
                orders.getContent().stream().map(this::mapToOrderResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), orders.getTotalElements()
        );
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        OrderStatus newStatus = OrderStatus.valueOf(request.getStatus());
        OrderStatus previousStatus = order.getStatus();

        order.setStatus(newStatus);

        if (newStatus == OrderStatus.SHIPPED) {
            order.setShippedAt(LocalDateTime.now());
            order.setTrackingNumber(request.getTrackingNumber());
            order.setTrackingUrl(request.getTrackingUrl());
        } else if (newStatus == OrderStatus.DELIVERED) {
            order.setDeliveredAt(LocalDateTime.now());
            order.setIsFulfilled(true);
        } else if (newStatus == OrderStatus.CANCELLED) {
            order.setCancelledAt(LocalDateTime.now());
            order.setIsCancelled(true);
        }

        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .previousStatus(previousStatus)
                .newStatus(newStatus)
                .comment(request.getNote())
                .customerNotified(request.isNotifyCustomer())
                .build();
        order.getStatusHistory().add(history);

        Order savedOrder = orderRepository.save(order);
        log.info("Order {} status changed from {} to {}", order.getOrderNumber(), previousStatus, newStatus);
        return mapToOrderResponse(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.REFUNDED) {
            throw new OrderException("Order is already cancelled or refunded");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        order.setIsCancelled(true);
        order.setCancelReason(reason);

        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .previousStatus(order.getStatus())
                .newStatus(OrderStatus.CANCELLED)
                .comment(reason)
                .build();
        order.getStatusHistory().add(history);

        return mapToOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponse addOrderNote(Long orderId, OrderNoteRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        OrderNote note = OrderNote.builder()
                .order(order)
                .message(request.getMessage())
                .isCustomerVisible(request.isCustomerVisible())
                .isInternal(request.isInternal())
                .build();
        order.getNotes().add(note);

        return mapToOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderSummary(Long orderId) {
        return getOrderById(orderId);
    }

    @Override
    public void sendOrderConfirmationEmail(Long orderId) {
        log.info("Sending order confirmation email for order: {}", orderId);
    }

    @Override
    public void sendShippingNotification(Long orderId) {
        log.info("Sending shipping notification for order: {}", orderId);
    }

    private void convertCartToOrder(Cart cart) {
        cart.setConverted(true);
        cart.setActive(false);
        cartRepository.save(cart);
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProductName())
                        .productSku(item.getProductSku())
                        .productImage(item.getProductImage())
                        .unitPrice(item.getUnitPrice())
                        .quantity(item.getQuantity())
                        .total(item.getTotal())
                        .isFulfilled(item.isFulfilled())
                        .requiresShipping(item.isRequiresShipping())
                        .build())
                .collect(Collectors.toList());

        List<OrderStatusHistoryResponse> historyResponses = order.getStatusHistory().stream()
                .map(h -> OrderStatusHistoryResponse.builder()
                        .id(h.getId())
                        .previousStatus(h.getPreviousStatus() != null ? h.getPreviousStatus().name() : null)
                        .newStatus(h.getNewStatus().name())
                        .comment(h.getComment())
                        .changedByName(h.getChangedByName())
                        .isSystemGenerated(h.isSystemGenerated())
                        .createdAt(h.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus().name())
                .paymentMethod(order.getPaymentMethod())
                .items(itemResponses)
                .itemCount(itemResponses.size())
                .subtotal(order.getSubtotal())
                .taxAmount(order.getTaxAmount())
                .shippingAmount(order.getShippingAmount())
                .discountAmount(order.getDiscountAmount())
                .total(order.getTotal())
                .currency(order.getCurrency())
                .trackingNumber(order.getTrackingNumber())
                .trackingUrl(order.getTrackingUrl())
                .isPaid(order.isPaid())
                .isFulfilled(order.isFulfilled())
                .isCancelled(order.isCancelled())
                .statusHistory(historyResponses)
                .customerNote(order.getCustomerNote())
                .createdAt(order.getCreatedAt())
                .build();
    }
}

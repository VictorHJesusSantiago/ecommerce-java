package com.ecommerce.service;

import com.ecommerce.model.dto.request.order.*;
import com.ecommerce.model.dto.response.order.OrderResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse createOrder(Long userId, CheckoutRequest request);

    OrderResponse getOrderById(Long id);

    OrderResponse getOrderbyNumber(String orderNumber);

    PaginatedResponse<OrderResponse> getUserOrders(Long userId, Pageable pageable);

    PaginatedResponse<OrderResponse> getAllOrders(Pageable pageable);

    PaginatedResponse<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable);

    OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request);

    OrderResponse cancelOrder(Long orderId, String reason);

    OrderResponse addOrderNote(Long orderId, OrderNoteRequest request);

    OrderResponse getOrderSummary(Long orderId);

    void sendOrderConfirmationEmail(Long orderId);

    void sendShippingNotification(Long orderId);
}

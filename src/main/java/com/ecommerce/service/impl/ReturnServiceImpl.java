package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.Return;
import com.ecommerce.model.entity.ReturnItem;
import com.ecommerce.model.entity.Order;
import com.ecommerce.model.entity.OrderItem;
import com.ecommerce.repository.ReturnRepository;
import com.ecommerce.repository.ReturnItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.ReturnService;
import com.ecommerce.util.CodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {

    private final ReturnRepository returnRepository;
    private final ReturnItemRepository returnItemRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Return createReturn(Long orderId, String reason, List<Long> orderItemIds) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        Return returnRequest = Return.builder()
                .returnNumber(CodeUtil.generateReturnNumber())
                .order(order)
                .reason(reason)
                .status("PENDING")
                .build();

        Return saved = returnRepository.save(returnRequest);

        BigDecimal totalRefund = BigDecimal.ZERO;
        for (Long orderItemId : orderItemIds) {
            OrderItem orderItem = order.getItems().stream()
                    .filter(i -> i.getId().equals(orderItemId))
                    .findFirst()
                    .orElse(null);
            if (orderItem != null) {
                ReturnItem item = ReturnItem.builder()
                        .returnRequest(saved)
                        .orderItem(orderItem)
                        .product(orderItem.getProduct())
                        .quantity(orderItem.getQuantity())
                        .unitPrice(orderItem.getUnitPrice())
                        .totalPrice(orderItem.getTotalPrice())
                        .reason(reason)
                        .build();
                returnItemRepository.save(item);
                totalRefund = totalRefund.add(orderItem.getTotalPrice());
            }
        }

        saved.setTotalRefundAmount(totalRefund);
        returnRepository.save(saved);

        log.info("Return created: {} for order {}", saved.getReturnNumber(), order.getOrderNumber());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Return getReturnById(Long id) {
        return returnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Return", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Return getReturnByNumber(String returnNumber) {
        return returnRepository.findByReturnNumber(returnNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Return", "number", returnNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Return> getReturnsByOrderId(Long orderId) {
        return returnRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public void processReturn(Long returnId, String status, BigDecimal refundAmount) {
        Return returnRequest = getReturnById(returnId);
        returnRequest.setStatus(status);
        if ("APPROVED".equals(status)) {
            returnRequest.setRefundAmount(refundAmount);
            returnRequest.setProcessedAt(java.time.LocalDateTime.now());
        }
        returnRepository.save(returnRequest);
    }

    @Override
    @Transactional
    public void approveReturn(Long returnId) {
        processReturn(returnId, "APPROVED", null);
    }

    @Override
    @Transactional
    public void rejectReturn(Long returnId, String reason) {
        Return returnRequest = getReturnById(returnId);
        returnRequest.setStatus("REJECTED");
        returnRequest.setRejectionReason(reason);
        returnRepository.save(returnRequest);
    }
}

package com.ecommerce.service;

import com.ecommerce.model.entity.Return;
import com.ecommerce.model.entity.ReturnItem;

import java.math.BigDecimal;
import java.util.List;

public interface ReturnService {
    Return createReturn(Long orderId, String reason, List<Long> orderItemIds);
    Return getReturnById(Long id);
    Return getReturnByNumber(String returnNumber);
    List<Return> getReturnsByOrderId(Long orderId);
    void processReturn(Long returnId, String status, BigDecimal refundAmount);
    void approveReturn(Long returnId);
    void rejectReturn(Long returnId, String reason);
}

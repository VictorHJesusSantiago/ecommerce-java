package com.ecommerce.service;

import com.ecommerce.model.entity.CustomerGroup;

import java.util.List;

public interface CustomerGroupService {
    List<CustomerGroup> getAllCustomerGroups();
    CustomerGroup getCustomerGroupById(Long id);
    CustomerGroup createCustomerGroup(String name, String description, String discountType, java.math.BigDecimal discountValue);
    CustomerGroup updateCustomerGroup(Long id, String name, String description);
    void deleteCustomerGroup(Long id);
    void addCustomerToGroup(Long groupId, Long userId);
    void removeCustomerFromGroup(Long groupId, Long userId);
}

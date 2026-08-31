package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.CustomerGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerGroupServiceImpl implements CustomerGroupService {

    private final CustomerGroupRepository customerGroupRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerGroup> getAllCustomerGroups() {
        return customerGroupRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerGroup getCustomerGroupById(Long id) {
        return customerGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerGroup", "id", id));
    }

    @Override
    @Transactional
    public CustomerGroup createCustomerGroup(String name, String description, String discountType, BigDecimal discountValue) {
        CustomerGroup group = CustomerGroup.builder()
                .name(name)
                .description(description)
                .discountType(discountType)
                .discountValue(discountValue)
                .build();
        return customerGroupRepository.save(group);
    }

    @Override
    @Transactional
    public CustomerGroup updateCustomerGroup(Long id, String name, String description) {
        CustomerGroup group = getCustomerGroupById(id);
        if (name != null) group.setName(name);
        if (description != null) group.setDescription(description);
        return customerGroupRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteCustomerGroup(Long id) {
        customerGroupRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addCustomerToGroup(Long groupId, Long userId) {
        CustomerGroup group = getCustomerGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        group.getUsers().add(user);
        customerGroupRepository.save(group);
    }

    @Override
    @Transactional
    public void removeCustomerFromGroup(Long groupId, Long userId) {
        CustomerGroup group = getCustomerGroupById(groupId);
        group.getUsers().removeIf(u -> u.getId().equals(userId));
        customerGroupRepository.save(group);
    }
}

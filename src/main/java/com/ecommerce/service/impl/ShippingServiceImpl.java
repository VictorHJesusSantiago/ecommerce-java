package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.ShippingService;
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
public class ShippingServiceImpl implements ShippingService {

    private final ShippingZoneRepository shippingZoneRepository;
    private final ShippingMethodRepository shippingMethodRepository;
    private final ShippingZoneRateRepository shippingZoneRateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ShippingZone> getAllShippingZones() {
        return shippingZoneRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ShippingZone getShippingZoneById(Long id) {
        return shippingZoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShippingZone", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShippingMethod> getAllShippingMethods() {
        return shippingMethodRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public ShippingMethod getShippingMethodById(Long id) {
        return shippingMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShippingMethod", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShippingMethod> getShippingMethodsForZone(Long zoneId) {
        return shippingZoneRateRepository.findByShippingZoneId(zoneId).stream()
                .map(ShippingZoneRate::getShippingMethod)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateShippingCost(Long methodId, BigDecimal weight, BigDecimal total) {
        ShippingMethod method = getShippingMethodById(methodId);
        return method.getRate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShippingMethod> getAvailableShippingMethods(String address, String country, String state, String zipCode) {
        return shippingMethodRepository.findByIsActiveTrue();
    }
}

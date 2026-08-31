package com.ecommerce.validation;

import com.ecommerce.repository.ProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueSkuValidator implements ConstraintValidator<UniqueSku, String> {

    private final ProductRepository productRepository;

    @Override
    public boolean isValid(String sku, ConstraintValidatorContext context) {
        if (sku == null || sku.isBlank()) return true;
        return !productRepository.existsBySku(sku);
    }
}

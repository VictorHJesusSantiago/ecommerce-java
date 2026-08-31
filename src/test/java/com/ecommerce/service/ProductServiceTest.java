package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.enums.ProductStatus;
import com.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private com.ecommerce.service.impl.ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .slug("test-product")
                .sku("TEST-001")
                .price(BigDecimal.valueOf(29.99))
                .status(ProductStatus.ACTIVE)
                .isActive(true)
                .isDeleted(false)
                .averageRating(4.5)
                .reviewCount(10L)
                .totalSold(100L)
                .viewCount(500L)
                .build();
    }

    @Test
    void getProductById_Exists_ReturnsProduct() {
        when(productRepository.findByIdWithImages(1L)).thenReturn(Optional.of(testProduct));

        var response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals("Test Product", response.getName());
        assertEquals("test-product", response.getSlug());
        assertEquals(BigDecimal.valueOf(29.99), response.getPrice());
    }

    @Test
    void getProductById_NotExists_ThrowsException() {
        when(productRepository.findByIdWithImages(999L)).thenThrow(
                new ResourceNotFoundException("Product", "id", 999L));

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void getProductBySlug_Exists_ReturnsProduct() {
        when(productRepository.findBySlug("test-product")).thenReturn(Optional.of(testProduct));

        var response = productService.getProductBySlug("test-product");

        assertNotNull(response);
        assertEquals("Test Product", response.getName());
    }

    @Test
    void deleteProduct_Exists_DeletesProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any())).thenReturn(testProduct);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).save(any());
        assertTrue(testProduct.getIsDeleted());
    }

    @Test
    void searchProducts_ReturnsResults() {
        var page = new org.springframework.data.domain.PageImpl<>(java.util.List.of(testProduct));
        when(productRepository.searchProducts(eq("test"), any())).thenReturn(page);

        var response = productService.searchProducts("test", org.springframework.data.domain.PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
    }
}

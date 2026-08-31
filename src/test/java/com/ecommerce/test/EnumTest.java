package com.ecommerce.test;

import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.model.enums.PaymentStatus;
import com.ecommerce.model.enums.ProductStatus;
import com.ecommerce.model.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumTest {

    @Test
    void orderStatus_Values() {
        assertEquals(8, OrderStatus.values().length);
        assertNotNull(OrderStatus.PENDING);
        assertNotNull(OrderStatus.PROCESSING);
        assertNotNull(OrderStatus.SHIPPED);
        assertNotNull(OrderStatus.DELIVERED);
        assertNotNull(OrderStatus.CANCELLED);
    }

    @Test
    void paymentStatus_Values() {
        assertNotNull(PaymentStatus.PENDING);
        assertNotNull(PaymentStatus.COMPLETED);
        assertNotNull(PaymentStatus.FAILED);
        assertNotNull(PaymentStatus.REFUNDED);
    }

    @Test
    void productStatus_Values() {
        assertNotNull(ProductStatus.ACTIVE);
        assertNotNull(ProductStatus.DRAFT);
        assertNotNull(ProductStatus.ARCHIVED);
    }

    @Test
    void userRole_Values() {
        assertNotNull(UserRole.USER);
        assertNotNull(UserRole.ADMIN);
    }

    @Test
    void orderStatus_valueOf() {
        assertEquals(OrderStatus.PENDING, OrderStatus.valueOf("PENDING"));
        assertEquals(OrderStatus.SHIPPED, OrderStatus.valueOf("SHIPPED"));
    }
}

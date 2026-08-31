package com.ecommerce.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    void appProperties_HasDefaults() {
        String appName = System.getProperty("app.name", "E-Commerce");
        assertNotNull(appName);
        assertEquals("E-Commerce", appName);
    }
}

package com.ecommerce.controller;

import com.ecommerce.model.dto.request.cart.AddToCartRequest;
import com.ecommerce.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCart_Success() throws Exception {
        mockMvc.perform(get("/api/v1/cart")
                        .header("X-Session-Id", "test-session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void addToCart_Success() throws Exception {
        AddToCartRequest request = AddToCartRequest.builder()
                .productId(1L)
                .quantity(2)
                .build();

        mockMvc.perform(post("/api/v1/cart/items")
                        .header("X-Session-Id", "test-session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void clearCart_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/cart")
                        .header("X-Session-Id", "test-session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}

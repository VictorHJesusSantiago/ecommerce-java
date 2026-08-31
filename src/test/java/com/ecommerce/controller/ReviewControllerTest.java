package com.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTopReviews_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/reviews/top")
                .param("limit", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void getReviewStats_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/reviews/stats"))
                .andExpect(status().isOk());
    }
}

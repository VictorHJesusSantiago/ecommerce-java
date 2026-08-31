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
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void searchProducts_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/search")
                .param("q", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void getSuggestions_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/search/suggestions")
                .param("q", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void getPopularSearches_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/search/popular")
                .param("limit", "10"))
                .andExpect(status().isOk());
    }
}

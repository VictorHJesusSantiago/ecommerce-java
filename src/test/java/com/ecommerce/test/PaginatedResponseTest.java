package com.ecommerce.test;

import com.ecommerce.model.dto.response.PaginatedResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginatedResponseTest {

    @Test
    void of_ReturnsCorrectResponse() {
        List<String> content = List.of("a", "b", "c");
        PaginatedResponse<String> response = PaginatedResponse.of(content, 0, 10, 25);

        assertNotNull(response);
        assertEquals(3, response.getContent().size());
        assertEquals(0, response.getPage());
        assertEquals(10, response.getSize());
        assertEquals(25, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
    }

    @Test
    void of_EmptyContent_ReturnsCorrectResponse() {
        List<String> content = List.of();
        PaginatedResponse<String> response = PaginatedResponse.of(content, 0, 10, 0);

        assertNotNull(response);
        assertEquals(0, response.getContent().size());
        assertEquals(0, response.getTotalElements());
    }

    @Test
    void of_IsLastPage() {
        List<String> content = List.of("a", "b");
        PaginatedResponse<String> response = PaginatedResponse.of(content, 2, 10, 25);

        assertTrue(response.isLast());
    }
}

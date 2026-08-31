package com.ecommerce.test;

import com.ecommerce.model.dto.response.ApiResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void success_WithData_ReturnsCorrectResponse() {
        ApiResponse<String> response = ApiResponse.success("test data");

        assertTrue(response.isSuccess());
        assertEquals("test data", response.getData());
        assertNull(response.getMessage());
    }

    @Test
    void success_WithMessageAndData_ReturnsCorrectResponse() {
        ApiResponse<String> response = ApiResponse.success("Success", "test data");

        assertTrue(response.isSuccess());
        assertEquals("Success", response.getMessage());
        assertEquals("test data", response.getData());
    }

    @Test
    void success_WithoutData_ReturnsCorrectResponse() {
        ApiResponse<Void> response = ApiResponse.success("Done");

        assertTrue(response.isSuccess());
        assertEquals("Done", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void error_ReturnsCorrectResponse() {
        ApiResponse<Void> response = ApiResponse.error("Something went wrong");

        assertFalse(response.isSuccess());
        assertEquals("Something went wrong", response.getMessage());
    }

    @Test
    void success_WithListData_ReturnsCorrectResponse() {
        List<String> data = List.of("a", "b", "c");
        ApiResponse<List<String>> response = ApiResponse.success(data);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(3, response.getData().size());
    }
}

package com.ecommerce.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenProvider, "jwtSecret", "dGhpcyBpcyBhIHZlcnkgbG9uZyBzZWNyZXQga2V5IGZvciBKV1QgdG9rZW4gc2lnbmluZyBwdXJwb3Nlcw==");
        ReflectionTestUtils.setField(tokenProvider, "jwtExpiration", 86400000L);
        ReflectionTestUtils.setField(tokenProvider, "refreshExpiration", 604800000L);
        ReflectionTestUtils.setField(tokenProvider, "issuer", "ecommerce-test");
    }

    @Test
    void generateToken_ValidUsername_ReturnsToken() {
        String token = tokenProvider.generateToken("test@example.com");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromToken_ValidToken_ReturnsUsername() {
        String token = tokenProvider.generateToken("test@example.com");

        String username = tokenProvider.getUsernameFromToken(token);

        assertEquals("test@example.com", username);
    }

    @Test
    void validateToken_ValidToken_ReturnsTrue() {
        String token = tokenProvider.generateToken("test@example.com");

        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        assertFalse(tokenProvider.validateToken("invalid-token"));
    }

    @Test
    void generateRefreshToken_ValidUsername_ReturnsToken() {
        String refreshToken = tokenProvider.generateRefreshToken("test@example.com");

        assertNotNull(refreshToken);
        assertFalse(refreshToken.isEmpty());
    }

    @Test
    void isTokenExpired_ValidToken_ReturnsFalse() {
        String token = tokenProvider.generateToken("test@example.com");

        assertFalse(tokenProvider.isTokenExpired(token));
    }
}

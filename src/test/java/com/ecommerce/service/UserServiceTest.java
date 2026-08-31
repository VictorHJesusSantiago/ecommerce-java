package com.ecommerce.service;

import com.ecommerce.exception.*;
import com.ecommerce.model.entity.User;
import com.ecommerce.model.enums.UserRole;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private com.ecommerce.service.impl.UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .username("testuser")
                .password("encoded_password")
                .firstName("Test")
                .lastName("User")
                .isEnabled(true)
                .isAccountLocked(false)
                .build();
    }

    @Test
    void register_Success() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        when(userRepository.save(any())).thenReturn(testUser);
        when(tokenProvider.generateToken(any())).thenReturn("test-token");
        when(tokenProvider.generateRefreshToken(any())).thenReturn("test-refresh-token");

        var request = new com.ecommerce.model.dto.request.auth.RegisterRequest();
        request.setEmail("test@example.com");
        request.setUsername("testuser");
        request.setPassword("Password123!");
        request.setFirstName("Test");
        request.setLastName("User");

        var response = userService.register(request);

        assertNotNull(response);
        assertEquals("test-token", response.getAccessToken());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void register_DuplicateEmail_ThrowsException() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        var request = new com.ecommerce.model.dto.request.auth.RegisterRequest();
        request.setEmail("test@example.com");
        request.setUsername("testuser");
        request.setPassword("Password123!");
        request.setFirstName("Test");
        request.setLastName("User");

        assertThrows(DuplicateResourceException.class, () -> userService.register(request));
    }

    @Test
    void getUserById_Exists_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        var response = userService.getUserById(1L);

        assertNotNull(response);
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Test User", response.getFullName());
    }

    @Test
    void getUserById_NotExists_ThrowsException() {
        when(userRepository.findById(999L)).thenThrow(
                new ResourceNotFoundException("User", "id", 999L));

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(999L));
    }

    @Test
    void enableUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any())).thenReturn(testUser);

        userService.enableUser(1L);

        assertTrue(testUser.isEnabled());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void disableUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any())).thenReturn(testUser);

        userService.disableUser(1L);

        assertFalse(testUser.isEnabled());
    }

    @Test
    void lockAccount_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any())).thenReturn(testUser);

        userService.lockAccount(1L);

        assertTrue(testUser.isAccountLocked());
    }

    @Test
    void unlockAccount_Success() {
        testUser.setAccountLocked(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any())).thenReturn(testUser);

        userService.unlockAccount(1L);

        assertFalse(testUser.isAccountLocked());
        assertEquals(0, testUser.getFailedLoginAttempts());
    }
}

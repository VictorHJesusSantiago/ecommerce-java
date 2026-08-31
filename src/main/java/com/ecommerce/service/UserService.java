package com.ecommerce.service;

import com.ecommerce.model.dto.request.auth.*;
import com.ecommerce.model.dto.response.auth.AuthResponse;
import com.ecommerce.model.dto.response.user.UserResponse;
import com.ecommerce.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(String token);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void changePassword(String email, ChangePasswordRequest request);

    void verifyEmail(String token);

    void resendVerificationEmail(String email);

    UserResponse getProfile(String email);

    UserResponse updateProfile(String email, UpdateProfileRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUsers(Pageable pageable);

    Page<UserResponse> searchUsers(String query, Pageable pageable);

    void deleteUser(Long id);

    void enableUser(Long id);

    void disableUser(Long id);

    void lockAccount(Long id);

    void unlockAccount(Long id);

    void assignRole(Long userId, String roleName);

    void removeRole(Long userId, String roleName);

    User getCurrentUser();

    AuthResponse setupTwoFactor(String email, String code);

    void disableTwoFactor(String email);

    List<UserResponse> getUsersByRole(String roleName, Pageable pageable);
}

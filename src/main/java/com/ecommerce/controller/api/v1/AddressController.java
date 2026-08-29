package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.request.user.AddressRequest;
import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.user.AddressResponse;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.model.entity.Address;
import com.ecommerce.model.entity.User;
import com.ecommerce.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "Addresses", description = "User address management endpoints")
public class AddressController {

    private final AddressRepository addressRepository;
    private final SecurityUtils securityUtils;

    @GetMapping
    @Operation(summary = "Get all user addresses")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddresses(
            @RequestAttribute("userId") Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        List<AddressResponse> responses = addresses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping
    @Operation(summary = "Create a new address")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody AddressRequest request) {
        User user = securityUtils.getCurrentUser().orElseThrow();
        Address address = Address.builder()
                .user(user)
                .addressType(com.ecommerce.model.enums.AddressType.valueOf(request.getAddressType()))
                .recipientName(request.getRecipientName())
                .phoneNumber(request.getPhoneNumber())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .isDefault(request.isDefault())
                .deliveryInstructions(request.getDeliveryInstructions())
                .build();

        if (request.isDefault()) {
            addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(a -> { a.setDefault(false); addressRepository.save(a); });
        }

        Address saved = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Address created", mapToResponse(saved)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an address")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Address", "id", id));

        if (request.getAddressType() != null) address.setAddressType(com.ecommerce.model.enums.AddressType.valueOf(request.getAddressType()));
        if (request.getRecipientName() != null) address.setRecipientName(request.getRecipientName());
        if (request.getAddressLine1() != null) address.setAddressLine1(request.getAddressLine1());
        if (request.getCity() != null) address.setCity(request.getCity());
        if (request.getState() != null) address.setState(request.getState());
        if (request.getPostalCode() != null) address.setPostalCode(request.getPostalCode());
        if (request.getCountry() != null) address.setCountry(request.getCountry());

        Address saved = addressRepository.save(address);
        return ResponseEntity.ok(ApiResponse.success("Address updated", mapToResponse(saved)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an address")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Address deleted", null));
    }

    @PutMapping("/{id}/default")
    @Operation(summary = "Set address as default")
    public ResponseEntity<ApiResponse<Void>> setDefaultAddress(@PathVariable Long id, @RequestAttribute("userId") Long userId) {
        addressRepository.findByUserIdAndIsDefaultTrue(userId)
                .ifPresent(a -> { a.setDefault(false); addressRepository.save(a); });
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Address", "id", id));
        address.setDefault(true);
        addressRepository.save(address);
        return ResponseEntity.ok(ApiResponse.success("Default address set", null));
    }

    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .addressType(address.getAddressType().name())
                .recipientName(address.getRecipientName())
                .phoneNumber(address.getPhoneNumber())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .isDefault(address.isDefault())
                .deliveryInstructions(address.getDeliveryInstructions())
                .createdAt(address.getCreatedAt())
                .build();
    }
}

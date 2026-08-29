package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.GiftCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/gift-cards")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Gift Cards", description = "Admin gift card management APIs")
public class AdminGiftCardController {

    private final GiftCardService giftCardService;

    @GetMapping
    @Operation(summary = "Get all gift cards")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllGiftCards() {
        return ResponseEntity.ok(ApiResponse.success(giftCardService.getAllGiftCards()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get gift card by ID")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getGiftCard(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(giftCardService.getGiftCardById(id)));
    }

    @PostMapping
    @Operation(summary = "Create gift card")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createGiftCard(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(giftCardService.createGiftCard(request)));
    }

    @PostMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate gift card")
    public ResponseEntity<ApiResponse<Void>> deactivateGiftCard(@PathVariable Long id) {
        giftCardService.deactivateGiftCard(id);
        return ResponseEntity.ok(ApiResponse.success("Gift card deactivated"));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get gift card statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getGiftCardStats() {
        return ResponseEntity.ok(ApiResponse.success(giftCardService.getGiftCardStats()));
    }
}

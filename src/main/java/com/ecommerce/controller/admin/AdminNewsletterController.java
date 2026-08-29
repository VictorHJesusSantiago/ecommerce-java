package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.newsletter.*;
import com.ecommerce.service.NewsletterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/newsletter")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Newsletter", description = "Admin newsletter management APIs")
public class AdminNewsletterController {

    private final NewsletterService newsletterService;

    @GetMapping("/subscribers")
    @Operation(summary = "Get all subscribers")
    public ResponseEntity<ApiResponse<PaginatedResponse<NewsletterResponse>>> getAllSubscribers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(newsletterService.getAllSubscribers(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @GetMapping("/subscribers/active")
    @Operation(summary = "Get active subscribers")
    public ResponseEntity<ApiResponse<PaginatedResponse<NewsletterResponse>>> getActiveSubscribers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(newsletterService.getActiveSubscribers(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @GetMapping("/subscribers/{id}")
    @Operation(summary = "Get subscriber by ID")
    public ResponseEntity<ApiResponse<NewsletterResponse>> getSubscriberById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(newsletterService.getById(id)));
    }

    @DeleteMapping("/subscribers/{id}")
    @Operation(summary = "Delete subscriber")
    public ResponseEntity<ApiResponse<Void>> deleteSubscriber(@PathVariable Long id) {
        newsletterService.deleteSubscriber(id);
        return ResponseEntity.ok(ApiResponse.success("Subscriber deleted"));
    }

    @GetMapping("/count")
    @Operation(summary = "Count active subscribers")
    public ResponseEntity<ApiResponse<Long>> countActive() {
        return ResponseEntity.ok(ApiResponse.success(newsletterService.countActive()));
    }

    @PostMapping("/tags")
    @Operation(summary = "Create tag")
    public ResponseEntity<ApiResponse<NewsletterTagResponse>> createTag(@RequestBody NewsletterTagRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tag created", newsletterService.createTag(request.getName(), request.getDescription())));
    }

    @GetMapping("/tags")
    @Operation(summary = "Get all tags")
    public ResponseEntity<ApiResponse<List<NewsletterTagResponse>>> getAllTags() {
        return ResponseEntity.ok(ApiResponse.success(newsletterService.getAllTags()));
    }

    @DeleteMapping("/tags/{id}")
    @Operation(summary = "Delete tag")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id) {
        newsletterService.deleteTag(id);
        return ResponseEntity.ok(ApiResponse.success("Tag deleted"));
    }
}

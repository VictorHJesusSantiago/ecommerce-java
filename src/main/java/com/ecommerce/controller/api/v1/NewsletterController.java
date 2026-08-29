package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.request.newsletter.NewsletterSubscriptionRequest;
import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.NewsletterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/newsletter")
@RequiredArgsConstructor
@Tag(name = "Newsletter", description = "Newsletter subscription APIs")
public class NewsletterController {

    private final NewsletterService newsletterService;

    @PostMapping("/subscribe")
    @Operation(summary = "Subscribe to newsletter")
    public ResponseEntity<ApiResponse<Void>> subscribe(
            @Valid @RequestBody NewsletterSubscriptionRequest request) {
        newsletterService.subscribe(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Subscribed successfully"));
    }

    @PostMapping("/unsubscribe")
    @Operation(summary = "Unsubscribe from newsletter")
    public ResponseEntity<ApiResponse<Void>> unsubscribe(@RequestParam String email) {
        newsletterService.unsubscribe(email);
        return ResponseEntity.ok(ApiResponse.success("Unsubscribed successfully"));
    }

    @GetMapping("/unsubscribe/{token}")
    @Operation(summary = "Unsubscribe via token")
    public ResponseEntity<ApiResponse<Void>> unsubscribeByToken(@PathVariable String token) {
        newsletterService.unsubscribeByToken(token);
        return ResponseEntity.ok(ApiResponse.success("Unsubscribed successfully"));
    }

    @GetMapping("/confirm/{email}")
    @Operation(summary = "Confirm subscription")
    public ResponseEntity<ApiResponse<Void>> confirmSubscription(@PathVariable String email) {
        newsletterService.confirmSubscription(email);
        return ResponseEntity.ok(ApiResponse.success("Subscription confirmed"));
    }

    @GetMapping("/check")
    @Operation(summary = "Check subscription status")
    public ResponseEntity<ApiResponse<Boolean>> checkSubscription(@RequestParam String email) {
        boolean subscribed = newsletterService.isSubscribed(email);
        return ResponseEntity.ok(ApiResponse.success(subscribed));
    }
}

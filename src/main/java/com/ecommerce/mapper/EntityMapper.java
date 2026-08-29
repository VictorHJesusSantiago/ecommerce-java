package com.ecommerce.mapper;

import com.ecommerce.model.entity.*;
import com.ecommerce.model.dto.response.product.*;
import com.ecommerce.model.dto.response.order.*;
import com.ecommerce.model.dto.response.user.*;
import com.ecommerce.model.dto.response.review.*;
import com.ecommerce.model.dto.response.payment.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .description(product.getDescription())
                .shortDescription(product.getShortDescription())
                .sku(product.getSku())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .price(product.getPrice())
                .compareAtPrice(product.getCompareAtPrice())
                .costPrice(product.getCostPrice())
                .taxRate(product.getTaxRate())
                .isTaxable(product.isTaxable())
                .isInclusiveTax(product.isInclusiveTax())
                .status(product.getStatus().name())
                .isActive(product.isActive())
                .isFeatured(product.isFeatured())
                .isDigital(product.isDigital())
                .requiresShipping(product.isRequiresShipping())
                .trackInventory(product.isTrackInventory())
                .weight(product.getWeight())
                .weightUnit(product.getWeightUnit())
                .averageRating(product.getAverageRating())
                .reviewCount(product.getReviewCount())
                .totalSold(product.getTotalSold())
                .viewCount(product.getViewCount())
                .wishlistCount(product.getWishlistCount())
                .images(product.getImages() != null ? product.getImages().stream()
                        .map(this::toProductImageResponse).collect(Collectors.toList()) : null)
                .variants(product.getProductVariants() != null ? product.getProductVariants().stream()
                        .map(this::toProductVariantResponse).collect(Collectors.toList()) : null)
                .attributes(product.getAttributes() != null ? product.getAttributes().stream()
                        .map(this::toProductAttributeResponse).collect(Collectors.toList()) : null)
                .tags(product.getTags() != null ? product.getTags().stream()
                        .map(this::toTagResponse).collect(Collectors.toList()) : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public ProductImageResponse toProductImageResponse(ProductImage image) {
        return ProductImageResponse.builder()
                .id(image.getId())
                .url(image.getUrl())
                .altText(image.getAltText())
                .title(image.getTitle())
                .isPrimary(image.isPrimary())
                .sortOrder(image.getSortOrder())
                .width(image.getWidth())
                .height(image.getHeight())
                .fileSize(image.getFileSize())
                .contentType(image.getContentType())
                .build();
    }

    public ProductVariantResponse toProductVariantResponse(ProductVariant variant) {
        return ProductVariantResponse.builder()
                .id(variant.getId())
                .name(variant.getName())
                .sku(variant.getSku())
                .barcode(variant.getBarcode())
                .price(variant.getPrice())
                .compareAtPrice(variant.getCompareAtPrice())
                .stockQuantity(variant.getStockQuantity())
                .reservedQuantity(variant.getReservedQuantity())
                .availableQuantity(variant.getAvailableQuantity())
                .trackInventory(variant.isTrackInventory())
                .isActive(variant.isActive())
                .weight(variant.getWeight())
                .imageUrl(variant.getImageUrl())
                .option1(variant.getOption1())
                .option2(variant.getOption2())
                .option3(variant.getOption3())
                .build();
    }

    public ProductAttributeResponse toProductAttributeResponse(ProductAttribute attr) {
        return ProductAttributeResponse.builder()
                .id(attr.getId())
                .attributeName(attr.getAttributeName())
                .attributeValue(attr.getAttributeValue())
                .attributeGroup(attr.getAttributeGroup())
                .sortOrder(attr.getSortOrder())
                .isFilterable(attr.isFilterable())
                .isSearchable(attr.isSearchable())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .parentId(category.getParentId())
                .imageUrl(category.getImageUrl())
                .iconUrl(category.getIconUrl())
                .bannerUrl(category.getBannerUrl())
                .color(category.getColor())
                .isActive(category.isActive())
                .isVisible(category.isVisible())
                .sortOrder(category.getSortOrder())
                .level(category.getLevel())
                .path(category.getPath())
                .productCount(category.getProductCount())
                .build();
    }

    public BrandResponse toBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .slug(brand.getSlug())
                .description(brand.getDescription())
                .logoUrl(brand.getLogoUrl())
                .bannerUrl(brand.getBannerUrl())
                .websiteUrl(brand.getWebsiteUrl())
                .isActive(brand.isActive())
                .isFeatured(brand.isFeatured())
                .sortOrder(brand.getSortOrder())
                .productCount(brand.getProductCount())
                .build();
    }

    public TagResponse toTagResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .description(tag.getDescription())
                .color(tag.getColor())
                .isActive(tag.isActive())
                .productCount(tag.getProductCount())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .bio(user.getBio())
                .isEnabled(user.isEnabled())
                .isAccountLocked(user.isAccountLocked())
                .isEmailVerified(user.isEmailVerified())
                .isTwoFactorEnabled(user.isTwoFactorEnabled())
                .preferredLanguage(user.getPreferredLanguage())
                .preferredCurrency(user.getPreferredCurrency())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public AddressResponse toAddressResponse(Address address) {
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
                .createdAt(address.getCreatedAt())
                .build();
    }

    public TransactionResponse toTransactionResponse(Transaction tx) {
        return TransactionResponse.builder()
                .id(tx.getId())
                .orderId(tx.getOrder().getId())
                .orderNumber(tx.getOrder().getOrderNumber())
                .transactionNumber(tx.getTransactionNumber())
                .paymentGateway(tx.getPaymentGateway())
                .paymentMethod(tx.getPaymentMethod())
                .status(tx.getStatus().name())
                .amount(tx.getAmount())
                .currency(tx.getCurrency())
                .fee(tx.getFee())
                .netAmount(tx.getNetAmount())
                .gatewayTransactionId(tx.getGatewayTransactionId())
                .cardLast4(tx.getCardLast4())
                .cardType(tx.getCardType())
                .isRefunded(tx.isRefunded())
                .refundedAmount(tx.getRefundedAmount())
                .createdAt(tx.getCreatedAt())
                .processedAt(tx.getProcessedAt())
                .build();
    }

    public RefundResponse toRefundResponse(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .refundNumber(refund.getRefundNumber())
                .orderId(refund.getOrder().getId())
                .orderNumber(refund.getOrder().getOrderNumber())
                .status(refund.getStatus().name())
                .amount(refund.getAmount())
                .currency(refund.getCurrency())
                .shippingAmount(refund.getShippingAmount())
                .taxAmount(refund.getTaxAmount())
                .reason(refund.getReason())
                .note(refund.getNote())
                .createdAt(refund.getCreatedAt())
                .processedAt(refund.getProcessedAt())
                .build();
    }

    public ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .productName(review.getProduct().getName())
                .userId(review.getUser().getId())
                .userName(review.getUser().getFullName())
                .userAvatar(review.getUser().getProfileImageUrl())
                .rating(review.getRating())
                .title(review.getTitle())
                .comment(review.getComment())
                .sellerResponse(review.getSellerResponse())
                .sellerResponseAt(review.getSellerResponseAt())
                .status(review.getStatus().name())
                .isVerifiedPurchase(review.isVerifiedPurchase())
                .helpfulCount(review.getHelpfulCount())
                .notHelpfulCount(review.getNotHelpfulCount())
                .images(review.getImages() != null ? review.getImages().stream()
                        .map(img -> ReviewImageResponse.builder()
                                .id(img.getId())
                                .url(img.getUrl())
                                .altText(img.getAltText())
                                .sortOrder(img.getSortOrder())
                                .build())
                        .collect(Collectors.toList()) : null)
                .createdAt(review.getCreatedAt())
                .build();
    }
}

package com.ecommerce.model.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CmsResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageResponse {
        private Long id;
        private String title;
        private String slug;
        private String content;
        private String excerpt;
        private String template;
        private String metaTitle;
        private String metaDescription;
        private boolean isPublished;
        private LocalDateTime publishedAt;
        private int sortOrder;
        private Long authorId;
        private String authorName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageRequest {
        private String title;
        private String slug;
        private String content;
        private String excerpt;
        private String template;
        private String metaTitle;
        private String metaDescription;
        private boolean isPublished;
        private LocalDateTime publishedAt;
        private int sortOrder;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuResponse {
        private Long id;
        private String name;
        private String location;
        private boolean isActive;
        private List<MenuItemResponse> items;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuRequest {
        private String name;
        private String location;
        private boolean isActive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuItemResponse {
        private Long id;
        private Long parentId;
        private String label;
        private String url;
        private String target;
        private String icon;
        private int sortOrder;
        private boolean isActive;
        private int depth;
        private List<MenuItemResponse> children;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuItemRequest {
        private String label;
        private String url;
        private String target;
        private String icon;
        private int sortOrder;
        private boolean isActive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BannerResponse {
        private Long id;
        private String title;
        private String description;
        private String imageUrl;
        private String linkUrl;
        private String position;
        private int sortOrder;
        private boolean isActive;
        private LocalDateTime startsAt;
        private LocalDateTime expiresAt;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BannerRequest {
        private String title;
        private String description;
        private String imageUrl;
        private String linkUrl;
        private String position;
        private int sortOrder;
        private boolean isActive;
        private LocalDateTime startsAt;
        private LocalDateTime expiresAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreSettingRequest {
        private String key;
        private String value;
        private String type;
        private String group;
        private String description;
    }
}

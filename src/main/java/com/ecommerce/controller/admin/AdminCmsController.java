package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.CmsService;
import com.ecommerce.model.dto.response.CmsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cms")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin CMS", description = "Admin content management APIs")
public class AdminCmsController {

    private final CmsService cmsService;

    // Pages
    @PostMapping("/pages")
    @Operation(summary = "Create page")
    public ResponseEntity<ApiResponse<CmsResponse.PageResponse>> createPage(@RequestBody CmsResponse.PageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Page created", cmsService.createPage(request)));
    }

    @PutMapping("/pages/{id}")
    @Operation(summary = "Update page")
    public ResponseEntity<ApiResponse<CmsResponse.PageResponse>> updatePage(
            @PathVariable Long id, @RequestBody CmsResponse.PageRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Page updated", cmsService.updatePage(id, request)));
    }

    @GetMapping("/pages/{id}")
    @Operation(summary = "Get page by ID")
    public ResponseEntity<ApiResponse<CmsResponse.PageResponse>> getPageById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(cmsService.getPageById(id)));
    }

    @GetMapping("/pages")
    @Operation(summary = "Get all published pages")
    public ResponseEntity<ApiResponse<List<CmsResponse.PageResponse>>> getAllPublishedPages() {
        return ResponseEntity.ok(ApiResponse.success(cmsService.getAllPublishedPages()));
    }

    @DeleteMapping("/pages/{id}")
    @Operation(summary = "Delete page")
    public ResponseEntity<ApiResponse<Void>> deletePage(@PathVariable Long id) {
        cmsService.deletePage(id);
        return ResponseEntity.ok(ApiResponse.success("Page deleted"));
    }

    @PutMapping("/pages/{id}/toggle")
    @Operation(summary = "Toggle page published status")
    public ResponseEntity<ApiResponse<Void>> togglePagePublished(@PathVariable Long id) {
        cmsService.togglePagePublished(id);
        return ResponseEntity.ok(ApiResponse.success("Page status updated"));
    }

    // Menus
    @PostMapping("/menus")
    @Operation(summary = "Create menu")
    public ResponseEntity<ApiResponse<CmsResponse.MenuResponse>> createMenu(
            @RequestBody CmsResponse.MenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Menu created", cmsService.createMenu(request.getName(), request.getLocation(), request.isActive())));
    }

    @GetMapping("/menus")
    @Operation(summary = "Get all menus")
    public ResponseEntity<ApiResponse<List<CmsResponse.MenuResponse>>> getAllMenus() {
        return ResponseEntity.ok(ApiResponse.success(cmsService.getAllMenus()));
    }

    @GetMapping("/menus/{id}")
    @Operation(summary = "Get menu by ID")
    public ResponseEntity<ApiResponse<CmsResponse.MenuResponse>> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(cmsService.getMenuById(id)));
    }

    @DeleteMapping("/menus/{id}")
    @Operation(summary = "Delete menu")
    public ResponseEntity<ApiResponse<Void>> deleteMenu(@PathVariable Long id) {
        cmsService.deleteMenu(id);
        return ResponseEntity.ok(ApiResponse.success("Menu deleted"));
    }

    // Menu Items
    @PostMapping("/menus/{menuId}/items")
    @Operation(summary = "Add menu item")
    public ResponseEntity<ApiResponse<CmsResponse.MenuItemResponse>> addMenuItem(
            @PathVariable Long menuId, @RequestBody CmsResponse.MenuItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Menu item added", cmsService.addMenuItem(menuId, request)));
    }

    @PostMapping("/menu-items/{parentId}/subitems")
    @Operation(summary = "Add sub menu item")
    public ResponseEntity<ApiResponse<CmsResponse.MenuItemResponse>> addSubMenuItem(
            @PathVariable Long parentId, @RequestBody CmsResponse.MenuItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Sub menu item added", cmsService.addSubMenuItem(parentId, request)));
    }

    @PutMapping("/menu-items/{id}")
    @Operation(summary = "Update menu item")
    public ResponseEntity<ApiResponse<CmsResponse.MenuItemResponse>> updateMenuItem(
            @PathVariable Long id, @RequestBody CmsResponse.MenuItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Menu item updated", cmsService.updateMenuItem(id, request)));
    }

    @DeleteMapping("/menu-items/{id}")
    @Operation(summary = "Delete menu item")
    public ResponseEntity<ApiResponse<Void>> deleteMenuItem(@PathVariable Long id) {
        cmsService.deleteMenuItem(id);
        return ResponseEntity.ok(ApiResponse.success("Menu item deleted"));
    }

    // Banners
    @PostMapping("/banners")
    @Operation(summary = "Create banner")
    public ResponseEntity<ApiResponse<CmsResponse.BannerResponse>> createBanner(@RequestBody CmsResponse.BannerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Banner created", cmsService.createBanner(
                        request.getTitle(), request.getDescription(), request.getImageUrl(),
                        request.getLinkUrl(), request.getPosition(), request.getSortOrder(), request.isActive())));
    }

    @GetMapping("/banners")
    @Operation(summary = "Get all banners")
    public ResponseEntity<ApiResponse<List<CmsResponse.BannerResponse>>> getAllBanners() {
        return ResponseEntity.ok(ApiResponse.success(cmsService.getAllBanners()));
    }

    @DeleteMapping("/banners/{id}")
    @Operation(summary = "Delete banner")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable Long id) {
        cmsService.deleteBanner(id);
        return ResponseEntity.ok(ApiResponse.success("Banner deleted"));
    }

    @PutMapping("/banners/{id}/toggle")
    @Operation(summary = "Toggle banner active status")
    public ResponseEntity<ApiResponse<Void>> toggleBannerActive(@PathVariable Long id) {
        cmsService.toggleBannerActive(id);
        return ResponseEntity.ok(ApiResponse.success("Banner status updated"));
    }

    // Store Settings
    @GetMapping("/settings")
    @Operation(summary = "Get all store settings")
    public ResponseEntity<ApiResponse<java.util.Map<String, String>>> getAllSettings() {
        return ResponseEntity.ok(ApiResponse.success(cmsService.getAllStoreSettings()));
    }

    @GetMapping("/settings/{key}")
    @Operation(summary = "Get store setting by key")
    public ResponseEntity<ApiResponse<String>> getSetting(@PathVariable String key) {
        return ResponseEntity.ok(ApiResponse.success(cmsService.getStoreSetting(key)));
    }

    @PostMapping("/settings")
    @Operation(summary = "Update store setting")
    public ResponseEntity<ApiResponse<Void>> updateSetting(@RequestBody CmsResponse.StoreSettingRequest request) {
        cmsService.updateStoreSetting(request.getKey(), request.getValue(), request.getType(), request.getGroup());
        return ResponseEntity.ok(ApiResponse.success("Setting updated"));
    }

    @DeleteMapping("/settings/{key}")
    @Operation(summary = "Delete store setting")
    public ResponseEntity<ApiResponse<Void>> deleteSetting(@PathVariable String key) {
        cmsService.deleteStoreSetting(key);
        return ResponseEntity.ok(ApiResponse.success("Setting deleted"));
    }
}

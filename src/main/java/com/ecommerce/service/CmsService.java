package com.ecommerce.service;

import com.ecommerce.model.dto.response.CmsResponse;
import com.ecommerce.model.entity.Banner;
import com.ecommerce.model.entity.Page;
import com.ecommerce.model.entity.Menu;
import com.ecommerce.model.entity.MenuItem;
import com.ecommerce.model.entity.StoreSetting;

import java.util.List;
import java.util.Map;

public interface CmsService {
    CmsResponse.PageResponse createPage(CmsResponse.PageRequest request);
    CmsResponse.PageResponse updatePage(Long id, CmsResponse.PageRequest request);
    CmsResponse.PageResponse getPageById(Long id);
    CmsResponse.PageResponse getPageBySlug(String slug);
    List<CmsResponse.PageResponse> getAllPublishedPages();
    void deletePage(Long id);
    void togglePagePublished(Long id);

    CmsResponse.MenuResponse createMenu(String name, String location, boolean isActive);
    List<CmsResponse.MenuResponse> getAllMenus();
    CmsResponse.MenuResponse getMenuById(Long id);
    CmsResponse.MenuResponse getMenuByLocation(String location);
    void deleteMenu(Long id);

    CmsResponse.MenuItemResponse addMenuItem(Long menuId, CmsResponse.MenuItemRequest request);
    CmsResponse.MenuItemResponse addSubMenuItem(Long parentId, CmsResponse.MenuItemRequest request);
    CmsResponse.MenuItemResponse updateMenuItem(Long id, CmsResponse.MenuItemRequest request);
    void deleteMenuItem(Long id);

    CmsResponse.BannerResponse createBanner(String title, String description, String imageUrl, String linkUrl, String position, int sortOrder, boolean isActive);
    List<CmsResponse.BannerResponse> getAllBanners();
    List<CmsResponse.BannerResponse> getActiveBannersByPosition(String position);
    void deleteBanner(Long id);
    void toggleBannerActive(Long id);

    void updateStoreSetting(String key, String value, String type, String group);
    String getStoreSetting(String key);
    Map<String, String> getAllStoreSettings();
    void deleteStoreSetting(String key);
}

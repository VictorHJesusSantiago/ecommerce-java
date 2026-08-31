package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.model.entity.*;
import com.ecommerce.model.dto.response.CmsResponse;
import com.ecommerce.repository.*;
import com.ecommerce.service.CmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CmsServiceImpl implements CmsService {

    private final PageRepository pageRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final BannerRepository bannerRepository;
    private final StoreSettingRepository storeSettingRepository;

    @Override
    @Transactional
    public CmsResponse.PageResponse createPage(CmsResponse.PageRequest request) {
        Page page = Page.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .content(request.getContent())
                .excerpt(request.getExcerpt())
                .template(request.getTemplate())
                .metaTitle(request.getMetaTitle())
                .metaDescription(request.getMetaDescription())
                .isPublished(request.isPublished())
                .publishedAt(request.isPublishedAt())
                .sortOrder(request.getSortOrder())
                .build();
        Page saved = pageRepository.save(page);
        return mapToPageResponse(saved);
    }

    @Override
    @Transactional
    public CmsResponse.PageResponse updatePage(Long id, CmsResponse.PageRequest request) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page", "id", id));
        if (request.getTitle() != null) page.setTitle(request.getTitle());
        if (request.getContent() != null) page.setContent(request.getContent());
        if (request.getMetaTitle() != null) page.setMetaTitle(request.getMetaTitle());
        if (request.getMetaDescription() != null) page.setMetaDescription(request.getMetaDescription());
        Page saved = pageRepository.save(page);
        return mapToPageResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CmsResponse.PageResponse getPageById(Long id) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page", "id", id));
        return mapToPageResponse(page);
    }

    @Override
    @Transactional(readOnly = true)
    public CmsResponse.PageResponse getPageBySlug(String slug) {
        Page page = pageRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Page", "slug", slug));
        return mapToPageResponse(page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsResponse.PageResponse> getAllPublishedPages() {
        return pageRepository.findByIsPublishedTrueOrderBySortOrderAsc().stream()
                .map(this::mapToPageResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePage(Long id) {
        pageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void togglePagePublished(Long id) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page", "id", id));
        page.setPublished(!page.isPublished());
        pageRepository.save(page);
    }

    @Override
    @Transactional
    public CmsResponse.MenuResponse createMenu(String name, String location, boolean isActive) {
        Menu menu = Menu.builder().name(name).location(location).isActive(isActive).build();
        Menu saved = menuRepository.save(menu);
        return mapToMenuResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsResponse.MenuResponse> getAllMenus() {
        return menuRepository.findAll().stream().map(this::mapToMenuResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CmsResponse.MenuResponse getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
        return mapToMenuResponse(menu);
    }

    @Override
    @Transactional(readOnly = true)
    public CmsResponse.MenuResponse getMenuByLocation(String location) {
        Menu menu = menuRepository.findByLocation(location)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "location", location));
        return mapToMenuResponse(menu);
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CmsResponse.MenuItemResponse addMenuItem(Long menuId, CmsResponse.MenuItemRequest request) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuId));
        MenuItem item = MenuItem.builder()
                .menu(menu).label(request.getLabel()).url(request.getUrl())
                .target(request.getTarget()).icon(request.getIcon())
                .sortOrder(request.getSortOrder()).isActive(request.isActive())
                .depth(0).build();
        MenuItem saved = menuItemRepository.save(item);
        return mapToMenuItemResponse(saved);
    }

    @Override
    @Transactional
    public CmsResponse.MenuItemResponse addSubMenuItem(Long parentId, CmsResponse.MenuItemRequest request) {
        MenuItem parent = menuItemRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "id", parentId));
        MenuItem item = MenuItem.builder()
                .menu(parent.getMenu()).parent(parent).label(request.getLabel()).url(request.getUrl())
                .target(request.getTarget()).icon(request.getIcon())
                .sortOrder(request.getSortOrder()).isActive(request.isActive())
                .depth(parent.getDepth() + 1).build();
        MenuItem saved = menuItemRepository.save(item);
        return mapToMenuItemResponse(saved);
    }

    @Override
    @Transactional
    public CmsResponse.MenuItemResponse updateMenuItem(Long id, CmsResponse.MenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "id", id));
        if (request.getLabel() != null) item.setLabel(request.getLabel());
        if (request.getUrl() != null) item.setUrl(request.getUrl());
        MenuItem saved = menuItemRepository.save(item);
        return mapToMenuItemResponse(saved);
    }

    @Override
    @Transactional
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CmsResponse.BannerResponse createBanner(String title, String description, String imageUrl, String linkUrl, String position, int sortOrder, boolean isActive) {
        Banner banner = Banner.builder()
                .title(title).description(description).imageUrl(imageUrl).linkUrl(linkUrl)
                .position(position).sortOrder(sortOrder).isActive(isActive)
                .build();
        Banner saved = bannerRepository.save(banner);
        return mapToBannerResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsResponse.BannerResponse> getAllBanners() {
        return bannerRepository.findAll().stream().map(this::mapToBannerResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsResponse.BannerResponse> getActiveBannersByPosition(String position) {
        return bannerRepository.findByPositionAndIsActiveTrueOrderBySortOrderAsc(position).stream()
                .map(this::mapToBannerResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void toggleBannerActive(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", "id", id));
        banner.setActive(!banner.isActive());
        bannerRepository.save(banner);
    }

    @Override
    @Transactional
    public void updateStoreSetting(String key, String value, String type, String group) {
        var existing = storeSettingRepository.findByKey(key);
        if (existing.isPresent()) {
            existing.get().setValue(value);
            storeSettingRepository.save(existing.get());
        } else {
            StoreSetting setting = StoreSetting.builder().key(key).value(value).type(type).group(group).build();
            storeSettingRepository.save(setting);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getStoreSetting(String key) {
        return storeSettingRepository.findByKey(key).map(StoreSetting::getValue).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getAllStoreSettings() {
        return storeSettingRepository.findAll().stream()
                .collect(Collectors.toMap(StoreSetting::getKey, StoreSetting::getValue));
    }

    @Override
    @Transactional
    public void deleteStoreSetting(String key) {
        storeSettingRepository.findByKey(key).ifPresent(storeSettingRepository::delete);
    }

    private CmsResponse.PageResponse mapToPageResponse(Page page) {
        return CmsResponse.PageResponse.builder()
                .id(page.getId()).title(page.getTitle()).slug(page.getSlug())
                .content(page.getContent()).excerpt(page.getExcerpt())
                .template(page.getTemplate())
                .metaTitle(page.getMetaTitle()).metaDescription(page.getMetaDescription())
                .isPublished(page.isPublished()).publishedAt(page.getPublishedAt())
                .sortOrder(page.getSortOrder())
                .createdAt(page.getCreatedAt()).updatedAt(page.getUpdatedAt())
                .build();
    }

    private CmsResponse.MenuResponse mapToMenuResponse(Menu menu) {
        return CmsResponse.MenuResponse.builder()
                .id(menu.getId()).name(menu.getName()).location(menu.getLocation())
                .isActive(menu.isActive()).items(menu.getItems() != null ? menu.getItems().stream().map(this::mapToMenuItemResponse).collect(Collectors.toList()) : List.of())
                .createdAt(menu.getCreatedAt())
                .build();
    }

    private CmsResponse.MenuItemResponse mapToMenuItemResponse(MenuItem item) {
        return CmsResponse.MenuItemResponse.builder()
                .id(item.getId()).label(item.getLabel()).url(item.getUrl())
                .target(item.getTarget()).icon(item.getIcon())
                .sortOrder(item.getSortOrder()).isActive(item.isActive())
                .depth(item.getDepth())
                .children(item.getChildren() != null ? item.getChildren().stream().map(this::mapToMenuItemResponse).collect(Collectors.toList()) : List.of())
                .build();
    }

    private CmsResponse.BannerResponse mapToBannerResponse(Banner banner) {
        return CmsResponse.BannerResponse.builder()
                .id(banner.getId()).title(banner.getTitle()).description(banner.getDescription())
                .imageUrl(banner.getImageUrl()).linkUrl(banner.getLinkUrl())
                .position(banner.getPosition()).sortOrder(banner.getSortOrder())
                .isActive(banner.isActive()).createdAt(banner.getCreatedAt())
                .build();
    }
}

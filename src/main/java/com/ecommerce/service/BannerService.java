package com.ecommerce.service;

import com.ecommerce.model.entity.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> getActiveBanners(String position);
    Banner getBannerById(Long id);
    List<Banner> getAllBanners();
}

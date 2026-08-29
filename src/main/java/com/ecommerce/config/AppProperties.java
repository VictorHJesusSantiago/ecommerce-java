package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProperties {

    @Value("${app.name:E-Commerce}")
    private String appName;

    @Value("${app.description:Full-featured e-commerce platform}")
    private String appDescription;

    @Value("${app.jwt.secret:defaultSecret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400}")
    private int jwtExpiration;

    @Value("${app.jwt.refresh-expiration:604800}")
    private int jwtRefreshExpiration;

    @Value("${app.upload.path:./uploads}")
    private String uploadPath;

    @Value("${app.upload.max-size:10485760}")
    private long uploadMaxSize;

    @Value("${app.upload.allowed-types:image/jpeg,image/png,image/gif,image/webp}")
    private String uploadAllowedTypes;

    @Value("${app.pagination.default-size:20}")
    private int defaultPageSize;

    @Value("${app.pagination.max-size:100}")
    private int maxPageSize;

    @Value("${app.cache.ttl:3600}")
    private int cacheTtl;

    @Value("${app.search.min-prefix:2}")
    private int searchMinPrefix;

    @Value("${app.search.max-results:100}")
    private int searchMaxResults;

    @Value("${app.email.from:noreply@ecommerce.com}")
    private String emailFrom;

    @Value("${app.store.name:My Store}")
    private String storeName;

    @Value("${app.store.url:http://localhost:8080}")
    private String storeUrl;

    public String getAppName() { return appName; }
    public String getAppDescription() { return appDescription; }
    public String getJwtSecret() { return jwtSecret; }
    public int getJwtExpiration() { return jwtExpiration; }
    public int getJwtRefreshExpiration() { return jwtRefreshExpiration; }
    public String getUploadPath() { return uploadPath; }
    public long getUploadMaxSize() { return uploadMaxSize; }
    public String getUploadAllowedTypes() { return uploadAllowedTypes; }
    public int getDefaultPageSize() { return defaultPageSize; }
    public int getMaxPageSize() { return maxPageSize; }
    public int getCacheTtl() { return cacheTtl; }
    public int getSearchMinPrefix() { return searchMinPrefix; }
    public int getSearchMaxResults() { return searchMaxResults; }
    public String getEmailFrom() { return emailFrom; }
    public String getStoreName() { return storeName; }
    public String getStoreUrl() { return storeUrl; }
}

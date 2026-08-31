package com.ecommerce.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageUtil {

    private PageUtil() {}

    public static Pageable createPageable(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return PageRequest.of(Math.max(0, page), Math.min(size, 100), sort);
    }

    public static Pageable createPageable(int page, int size) {
        return PageRequest.of(Math.max(0, page), Math.min(size, 100));
    }

    public static Pageable createDefaultPageable() {
        return PageRequest.of(0, 20, Sort.by("createdAt").descending());
    }
}

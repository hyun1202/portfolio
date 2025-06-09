package com.numo.portfolio.category.adapter.in.web.dto;

import com.numo.portfolio.category.domain.Category;

public record CategoryResponse(
        String categoryName,
        int index
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getCategoryName(),
                category.getIndex()
        );
    }
}

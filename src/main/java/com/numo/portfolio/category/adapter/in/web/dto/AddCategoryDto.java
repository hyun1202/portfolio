package com.numo.portfolio.category.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;

public record AddCategoryDto(
        @NotNull(message = "카테고리 이름은 필수입니다.")
        String categoryName
) {
}

package com.numo.portfolio.category.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCategoryDto(
        @NotNull(message = "카테고리 이름은 필수입니다.")
        String categoryName,
        @Min(value = 0, message = "인덱스는 0 이상이어야 합니다.")
        @NotNull(message = "인덱스는 필수입니다.")
        int index
) {
}

package com.numo.portfolio.category.application.port.out;

import com.numo.portfolio.category.domain.Category;

import java.util.List;

public interface GetCategoryQueryPort {
    List<Category> findCategories(Long userId);
    int findLastIndex(Long userId);
}

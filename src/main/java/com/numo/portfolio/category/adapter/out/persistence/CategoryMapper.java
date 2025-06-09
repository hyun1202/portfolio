package com.numo.portfolio.category.adapter.out.persistence;

import com.numo.portfolio.category.adapter.out.persistence.jpa.CategoryEntity;
import com.numo.portfolio.category.domain.Category;
import com.numo.portfolio.user.adapter.out.persistence.jpa.UserEntity;

public abstract class CategoryMapper {
    public static CategoryEntity mapToEntity(Category category) {
        return CategoryEntity.builder()
                .user(new UserEntity(category.getUser().getId()))
                .index(category.getIndex())
                .categoryName(category.getCategoryName())
                .build();
    }

    public static Category mapToCategory(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .index(entity.getIndex())
                .categoryName(entity.getCategoryName())
                .build();
    }
}

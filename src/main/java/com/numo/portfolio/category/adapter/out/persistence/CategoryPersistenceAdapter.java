package com.numo.portfolio.category.adapter.out.persistence;

import com.numo.portfolio.category.adapter.out.persistence.jpa.CategoryEntity;
import com.numo.portfolio.category.adapter.out.persistence.jpa.CategoryJpaRepository;
import com.numo.portfolio.category.application.port.in.CheckCategoryDuplicatePort;
import com.numo.portfolio.category.application.port.out.AddCategoryPort;
import com.numo.portfolio.category.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements AddCategoryPort, CheckCategoryDuplicatePort {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(Category category) {
        CategoryEntity entity = CategoryMapper.mapToEntity(category);
        CategoryEntity savedEntity = categoryJpaRepository.save(entity);
        return CategoryMapper.mapToCategory(savedEntity);
    }

    @Override
    public boolean isDuplicated(Long userId, String categoryName) {
        return categoryJpaRepository.existsByUser_IdAndCategoryName(userId, categoryName);
    }
}

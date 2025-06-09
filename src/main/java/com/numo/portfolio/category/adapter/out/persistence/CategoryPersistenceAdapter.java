package com.numo.portfolio.category.adapter.out.persistence;

import com.numo.portfolio.category.adapter.out.persistence.jpa.CategoryEntity;
import com.numo.portfolio.category.adapter.out.persistence.jpa.CategoryJpaRepository;
import com.numo.portfolio.category.application.port.out.CheckCategoryDuplicatePort;
import com.numo.portfolio.category.application.port.out.AddCategoryPort;
import com.numo.portfolio.category.application.port.out.GetCategoryQueryPort;
import com.numo.portfolio.category.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements
        AddCategoryPort,
        CheckCategoryDuplicatePort,
        GetCategoryQueryPort
{
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

    @Override
    public List<Category> findCategories(Long userId) {
        List<CategoryEntity> entities = categoryJpaRepository.findCategoryEntitiesByUser_Id(userId);
        return entities.stream().map(CategoryMapper::mapToCategory).toList();
    }

    @Override
    public int findLastIndex(Long userId) {
        return categoryJpaRepository.findTopByUser_IdOrderByIndexDesc(userId)
                .map(CategoryEntity::getIndex)
                .orElse(0);
    }
}

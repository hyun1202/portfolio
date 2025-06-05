package com.numo.portfolio.category.adapter.out.persistence;

import com.numo.portfolio.category.adapter.out.persistence.jpa.CategoryEntity;
import com.numo.portfolio.category.adapter.out.persistence.jpa.CategoryJpaRepository;
import com.numo.portfolio.category.application.port.out.AddCategoryPort;
import com.numo.portfolio.category.comm.exception.CategoryErrorCode;
import com.numo.portfolio.comm.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements AddCategoryPort {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Long save(Long userId, String categoryName, int index) {
        if (categoryJpaRepository.existsByUser_IdAndCategoryName(userId, categoryName)) {
            throw new CustomException(CategoryErrorCode.DUPLICATED_CATEGORY);
        }

        CategoryEntity entity = CategoryEntity.builder()
                .categoryName(categoryName)
                .index(index)
                .build();

        return categoryJpaRepository.save(entity).getId();
    }
}

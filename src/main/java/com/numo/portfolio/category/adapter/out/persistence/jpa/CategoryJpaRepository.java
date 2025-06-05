package com.numo.portfolio.category.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByUser_IdAndCategoryName(Long userId, String categoryName);
}

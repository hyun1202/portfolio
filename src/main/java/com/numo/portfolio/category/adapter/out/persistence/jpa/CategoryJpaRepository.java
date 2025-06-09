package com.numo.portfolio.category.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByUser_IdAndCategoryName(Long userId, String categoryName);
    List<CategoryEntity> findCategoryEntitiesByUser_Id(Long userId);
    Optional<CategoryEntity> findTopByUser_IdOrderByIndexDesc(Long userId);
}

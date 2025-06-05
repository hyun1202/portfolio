package com.numo.portfolio.category.application.port.out;

public interface AddCategoryPort {
    Long save(Long userId, String categoryName, int index);
}

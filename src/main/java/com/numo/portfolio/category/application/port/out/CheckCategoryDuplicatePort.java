package com.numo.portfolio.category.application.port.out;

public interface CheckCategoryDuplicatePort {
    boolean isDuplicated(Long userId, String categoryName);
}

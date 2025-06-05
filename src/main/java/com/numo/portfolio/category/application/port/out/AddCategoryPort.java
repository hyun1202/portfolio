package com.numo.portfolio.category.application.port.out;

import com.numo.portfolio.category.domain.Category;

public interface AddCategoryPort {
    Category save(Category category);
}

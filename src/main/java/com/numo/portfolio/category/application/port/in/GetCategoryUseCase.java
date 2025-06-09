package com.numo.portfolio.category.application.port.in;

import com.numo.portfolio.category.application.GetCategoryCommand;
import com.numo.portfolio.category.domain.Category;

import java.util.List;

public interface GetCategoryUseCase {
    List<Category> getCategories(GetCategoryCommand command);
}

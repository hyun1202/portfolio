package com.numo.portfolio.category.application.port.in;

import com.numo.portfolio.category.application.command.AddCategoryCommand;

public interface AddCategoryUseCase {
    Long addCategory(AddCategoryCommand command);
}

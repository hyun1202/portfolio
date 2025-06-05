package com.numo.portfolio.category.application.command;

public record AddCategoryCommand(
        Long userId,
        String categoryName,
        int index
) {
}

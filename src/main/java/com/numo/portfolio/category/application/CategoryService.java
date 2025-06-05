package com.numo.portfolio.category.application;

import com.numo.portfolio.category.application.command.AddCategoryCommand;
import com.numo.portfolio.category.application.port.in.AddCategoryUseCase;
import com.numo.portfolio.category.application.port.out.AddCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements AddCategoryUseCase {
    private final AddCategoryPort addCategoryPort;

    @Override
    public Long addCategory(AddCategoryCommand command) {
        return addCategoryPort.save(
                command.userId(),
                command.categoryName(),
                command.index()
        );
    }
}

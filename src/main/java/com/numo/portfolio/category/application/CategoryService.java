package com.numo.portfolio.category.application;

import com.numo.portfolio.category.application.command.AddCategoryCommand;
import com.numo.portfolio.category.application.port.in.AddCategoryUseCase;
import com.numo.portfolio.category.application.port.in.CheckCategoryDuplicatePort;
import com.numo.portfolio.category.application.port.out.AddCategoryPort;
import com.numo.portfolio.category.comm.exception.CategoryErrorCode;
import com.numo.portfolio.category.domain.Category;
import com.numo.portfolio.comm.exception.CustomException;
import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
import com.numo.portfolio.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements AddCategoryUseCase {
    private final GetUserQueryPort getUserQueryPort;
    private final AddCategoryPort addCategoryPort;
    private final CheckCategoryDuplicatePort categoryDuplicatePort;

    @Override
    public Long addCategory(AddCategoryCommand command) {
        User user = getUserQueryPort.getUserById(command.userId());

        validateForCreation(command);

        Category category = Category.builder()
                .user(user)
                .categoryName(command.categoryName())
                .index(command.index())
                .build();

        return addCategoryPort.save(category).getId();
    }

    private void validateForCreation(AddCategoryCommand command) {
        // 중복 검증
        if (categoryDuplicatePort.isDuplicated(command.userId(), command.categoryName())) {
            throw new CustomException(CategoryErrorCode.DUPLICATED_CATEGORY);
        }
    }
}

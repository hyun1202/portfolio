package com.numo.portfolio.category.adapter.in.web;

import com.numo.portfolio.category.adapter.in.web.dto.AddCategoryDto;
import com.numo.portfolio.category.adapter.in.web.dto.CategoryResponse;
import com.numo.portfolio.category.application.GetCategoryCommand;
import com.numo.portfolio.category.application.command.AddCategoryCommand;
import com.numo.portfolio.category.application.port.in.AddCategoryUseCase;
import com.numo.portfolio.category.application.port.in.GetCategoryUseCase;
import com.numo.portfolio.category.domain.Category;
import com.numo.portfolio.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final AddCategoryUseCase addCategoryUsecase;
    private final GetCategoryUseCase getCategoryUseCase;

    @PostMapping
    public ResponseEntity<Long> addCategory(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody @Valid AddCategoryDto req) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(
                userDetails.getUser().getId(),
                req.categoryName()
        );

        Long id = addCategoryUsecase.addCategory(addCategoryCommand);

        return ResponseEntity.ok(id);
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        GetCategoryCommand getCategoryCommand = new GetCategoryCommand(
                userDetails.getUser().getId()
        );

        List<Category> categories = getCategoryUseCase.getCategories(getCategoryCommand);
        List<CategoryResponse> res = categories.stream().map(CategoryResponse::from).toList();

        return ResponseEntity.ok(res);
    }
}

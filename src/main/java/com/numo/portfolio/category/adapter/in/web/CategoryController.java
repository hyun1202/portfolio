package com.numo.portfolio.category.adapter.in.web;

import com.numo.portfolio.category.adapter.in.web.dto.AddCategoryDto;
import com.numo.portfolio.category.application.command.AddCategoryCommand;
import com.numo.portfolio.category.application.port.in.AddCategoryUseCase;
import com.numo.portfolio.category.domain.Category;
import com.numo.portfolio.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final AddCategoryUseCase addCategoryUsecase;

    @PostMapping
    public ResponseEntity<Long> addCategory(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody @Valid AddCategoryDto req) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(
                userDetails.getUser().getId(),
                req.categoryName(),
                req.index()
        );

        Long id = addCategoryUsecase.addCategory(addCategoryCommand);

        return ResponseEntity.ok(id);
    }

}

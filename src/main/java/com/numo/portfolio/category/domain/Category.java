package com.numo.portfolio.category.domain;

import com.numo.portfolio.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Category {
    private final Long id;
    private final User user;
    private final String categoryName;
    private final int index;
}

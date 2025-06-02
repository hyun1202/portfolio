package com.numo.portfolio.user.adapter.in.web.dto;

import com.numo.portfolio.user.domain.SocialType;
import com.numo.portfolio.user.domain.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String email,
        String socialId,
        SocialType socialType,
        String nickname,
        String domain
) {
}

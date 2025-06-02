package com.numo.portfolio.user.application.command;

import com.numo.portfolio.user.domain.SocialType;

public record AddUserCommand(
        String socialId,
        SocialType socialType,
        String nickname
) {
}

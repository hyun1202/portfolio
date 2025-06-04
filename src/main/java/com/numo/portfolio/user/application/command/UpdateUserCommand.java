package com.numo.portfolio.user.application.command;

import com.numo.portfolio.user.domain.User;

public record UpdateUserCommand(
        User user,
        String nickname
) {
}

package com.numo.portfolio.user.application.port.in;

import com.numo.portfolio.user.application.command.WithdrawUserCommand;

public interface WithdrawUserUsecase {
    void withdrawUser(WithdrawUserCommand command);
}

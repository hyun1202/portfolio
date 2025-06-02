package com.numo.portfolio.user.application.port.in;

import com.numo.portfolio.user.application.command.AddUserCommand;

public interface AddUserUseCase {
    Long signIn(AddUserCommand command);
}

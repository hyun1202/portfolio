package com.numo.portfolio.user.application.port.in;

import com.numo.portfolio.user.application.command.UpdateUserCommand;

public interface UpdateUserUseCase {
    Long updateUser(UpdateUserCommand command);
}

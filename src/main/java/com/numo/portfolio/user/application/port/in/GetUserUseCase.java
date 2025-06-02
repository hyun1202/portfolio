package com.numo.portfolio.user.application.port.in;

import com.numo.portfolio.user.adapter.in.web.dto.UserResponse;
import com.numo.portfolio.user.application.command.GetUserCommand;
import com.numo.portfolio.user.domain.User;

public interface GetUserUseCase {
    UserResponse getUser(GetUserCommand getUserCommand);
}

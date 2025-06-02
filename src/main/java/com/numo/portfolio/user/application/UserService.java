package com.numo.portfolio.user.application;

import com.numo.portfolio.user.adapter.in.web.dto.UserResponse;
import com.numo.portfolio.user.application.command.GetUserCommand;
import com.numo.portfolio.user.application.port.in.GetUserUseCase;
import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
import com.numo.portfolio.user.domain.User;
import com.numo.portfolio.user.application.command.AddUserCommand;
import com.numo.portfolio.user.application.port.in.AddUserUseCase;
import com.numo.portfolio.user.application.port.out.AddUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements AddUserUseCase, GetUserUseCase {
    private final AddUserPort addUserPort;
    private final GetUserQueryPort getUserQueryPort;

    @Override
    public Long signIn(AddUserCommand command) {
        User user = User.builder()
                .socialId(command.socialId())
                .socialType(command.socialType())
                .nickname(command.nickname())
                .build();
        return addUserPort.createUser(user).getId();
    }

    @Override
    public UserResponse getUser(GetUserCommand getUserCommand) {
        User user = getUserQueryPort.getUserBySocialId(getUserCommand.socialId());

        return UserResponse.builder()
                .id(user.getId())
                .socialId(user.getSocialId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .domain(user.getDomain())
                .socialType(user.getSocialType())
                .build();
    }
}

package com.numo.portfolio.user.application;

import com.numo.portfolio.user.domain.User;
import com.numo.portfolio.user.application.command.AddUserCommand;
import com.numo.portfolio.user.application.port.in.AddUserUseCase;
import com.numo.portfolio.user.application.port.out.AddUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements AddUserUseCase {
    private final AddUserPort addUserPort;

    @Override
    public Long signIn(AddUserCommand command) {
        User user = User.builder()
                .socialId(command.socialId())
                .socialType(command.socialType())
                .nickname(command.nickname())
                .build();
        return addUserPort.createUser(user).getId();
    }
}

package com.numo.portfolio.user.application;

import com.numo.portfolio.user.adapter.in.web.dto.UserResponse;
import com.numo.portfolio.user.application.command.AddUserCommand;
import com.numo.portfolio.user.application.command.GetUserCommand;
import com.numo.portfolio.user.application.command.UpdateDomainCommand;
import com.numo.portfolio.user.application.command.UpdateUserCommand;
import com.numo.portfolio.user.application.port.in.AddUserUseCase;
import com.numo.portfolio.user.application.port.in.GetUserUseCase;
import com.numo.portfolio.user.application.port.in.UpdateDomainUseCase;
import com.numo.portfolio.user.application.port.in.UpdateUserUseCase;
import com.numo.portfolio.user.application.port.out.AddUserPort;
import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
import com.numo.portfolio.user.application.port.out.UpdateDomainPort;
import com.numo.portfolio.user.application.port.out.UpdateUserPort;
import com.numo.portfolio.user.domain.User;
import com.numo.portfolio.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements
        AddUserUseCase,
        GetUserUseCase,
        UpdateDomainUseCase,
        UpdateUserUseCase
{
    private final AddUserPort addUserPort;
    private final GetUserQueryPort getUserQueryPort;
    private final UpdateDomainPort updateDomainPort;
    private final UpdateUserPort updateUserPort;

    @Override
    public Long signIn(AddUserCommand command) {
        User user = User.builder()
                .socialId(command.socialId())
                .socialType(command.socialType())
                .nickname(command.nickname())
                .role(UserRole.ROLE_USER)
                .build();

        return addUserPort.createUser(user).getId();
    }

    @Override
    public UserResponse getUser(GetUserCommand command) {
        User user = getUserQueryPort.getUserById(command.user().getId());

        return UserResponse.builder()
                .id(user.getId())
                .socialId(user.getSocialId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .domain(user.getDomain())
                .socialType(user.getSocialType())
                .build();
    }

    @Transactional
    @Override
    public Long updateUserDomain(UpdateDomainCommand command) {
        return updateDomainPort.updateDomain(
                command.user().getId(),
                command.domain()
        );
    }

    @Transactional
    @Override
    public Long updateUser(UpdateUserCommand command) {
        return updateUserPort.updateUser(
                command.user().getId(),
                command.nickname()
        );
    }
}

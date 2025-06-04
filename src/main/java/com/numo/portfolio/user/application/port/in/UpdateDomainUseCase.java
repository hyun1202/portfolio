package com.numo.portfolio.user.application.port.in;

import com.numo.portfolio.user.application.command.UpdateDomainCommand;

public interface UpdateDomainUseCase {
    Long updateUserDomain(UpdateDomainCommand command);
}

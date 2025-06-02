package com.numo.portfolio.user.application.port.out;

import com.numo.portfolio.user.domain.User;

public interface AddUserPort {
    User createUser(User user);
}

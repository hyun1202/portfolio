package com.numo.portfolio.user.application.port.out;

import com.numo.portfolio.user.domain.User;

public interface GetUserQuery {
    User getUser(Long userId);
}

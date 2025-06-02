package com.numo.portfolio.user.application.port.out;

import com.numo.portfolio.user.domain.User;

import java.util.Optional;

public interface GetUserQueryPort {
    User getUserBySocialId(String socialId);
}

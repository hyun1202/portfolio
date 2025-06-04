package com.numo.portfolio.user.application.port.out;

import com.numo.portfolio.user.domain.User;

public interface GetUserQueryPort {
    User getUserBySocialId(String socialId);
    User getUserById(Long userId);
}

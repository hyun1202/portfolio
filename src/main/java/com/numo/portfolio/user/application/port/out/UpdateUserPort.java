package com.numo.portfolio.user.application.port.out;

public interface UpdateUserPort {
    Long updateUser(Long userId, String nickname);
    Long updateDomain(Long userId, String domain);
    void withdrawUser(Long userId);
}

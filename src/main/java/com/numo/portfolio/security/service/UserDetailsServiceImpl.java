package com.numo.portfolio.security.service;

import com.numo.portfolio.comm.exception.CustomException;
import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
import com.numo.portfolio.user.comm.exception.UserErrorCode;
import com.numo.portfolio.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final GetUserQueryPort getUserQueryPort;

    @Override
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
        return Optional.ofNullable(getUserQueryPort.getUserBySocialId(socialId))
                .map(this::createUser)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    private UserDetails createUser(User user) {
        if (!user.isActivatedUser()) {
            throw new CustomException(UserErrorCode.WITHDRAW_USER);
        }
        return new UserDetailsImpl(user);
    }
}


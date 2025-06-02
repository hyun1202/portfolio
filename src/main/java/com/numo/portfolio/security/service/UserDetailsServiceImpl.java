package com.numo.portfolio.security.service;

import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
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
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUser(User user) {
        if (!user.isActivatedUser()) {
            throw new IllegalArgumentException("탈퇴한 계정입니다.");
        }
        return new UserDetailsImpl(user);
    }
}


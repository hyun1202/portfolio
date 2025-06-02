package com.numo.portfolio.user.domain;

import com.numo.portfolio.user.adapter.out.entity.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Getter
@RequiredArgsConstructor
public class User {
    private final Long id;
    private final String email;
    private final String socialId;
    private final SocialType socialType;
    private final String nickname;
    private final String domain;
    private final LocalDateTime withdrawAt;

    public boolean isActivatedUser() {
        return Objects.isNull(withdrawAt);
    }
}

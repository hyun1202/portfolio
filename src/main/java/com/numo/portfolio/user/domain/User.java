package com.numo.portfolio.user.domain;

import com.numo.portfolio.comm.domain.Timestamped;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class User extends Timestamped {
    private final Long id;
    private final String email;
    private final String socialId;
    private final SocialType socialType;
    private final String nickname;
    private final String domain;
    private final LocalDateTime withdrawAt;
    private final UserRole role;

    @Builder
    public User(LocalDateTime createdAt,
                LocalDateTime modifiedAt,
                Long id,
                String email,
                String socialId,
                SocialType socialType,
                String nickname,
                String domain,
                LocalDateTime withdrawAt,
                UserRole role) {
        super(createdAt, modifiedAt);
        this.id = id;
        this.email = email;
        this.socialId = socialId;
        this.socialType = socialType;
        this.nickname = nickname;
        this.domain = domain;
        this.withdrawAt = withdrawAt;
        this.role = role;
    }

    public boolean isActivatedUser() {
        return Objects.isNull(withdrawAt);
    }
}

package com.numo.portfolio.user.adapter.out.persistence.jpa;

import com.numo.portfolio.comm.persistence.TimestampedEntity;
import com.numo.portfolio.user.domain.SocialType;
import com.numo.portfolio.user.domain.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user")
public class UserEntity extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @Column(unique = true)
    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String nickname;
    @Column(unique = true)
    private String domain;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime withdrawAt;

    public void updateDomain(String domain) {
        this.domain = domain;
    }

    public void updateUser(String nickname) {
        this.nickname = nickname;
    }
}

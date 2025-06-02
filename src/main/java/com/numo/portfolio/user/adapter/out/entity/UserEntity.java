package com.numo.portfolio.user.adapter.out.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user")
public class UserEntity {
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
    private LocalDateTime withdrawAt;
}

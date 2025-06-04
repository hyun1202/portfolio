package com.numo.portfolio.user.adapter.out.persistence;

import com.numo.portfolio.security.oauth2.info.OAuth2UserInfo;
import com.numo.portfolio.user.adapter.out.persistence.jpa.UserEntity;
import com.numo.portfolio.user.domain.SocialType;
import com.numo.portfolio.user.domain.User;
import com.numo.portfolio.user.domain.UserRole;

abstract class UserMapper {
    static User mapToUser(UserEntity userEntity) {
        return User.builder()
                .email(userEntity.getEmail())
                .domain(userEntity.getDomain())
                .socialId(userEntity.getSocialId())
                .socialType(userEntity.getSocialType())
                .nickname(userEntity.getNickname())
                .id(userEntity.getId())
                .createdAt(userEntity.getCreatedAt())
                .modifiedAt(userEntity.getModifiedAt())
                .withdrawAt(userEntity.getWithdrawAt())
                .role(userEntity.getRole())
                .build();
    }

    static UserEntity mapToUserEntity(OAuth2UserInfo oAuth2UserInfo) {
        return UserEntity.builder()
                .nickname(oAuth2UserInfo.nickname())
                .email(oAuth2UserInfo.email())
                .socialType(SocialType.getType(oAuth2UserInfo.clientName()))
                .role(UserRole.ROLE_USER)
                .socialId(oAuth2UserInfo.socialId())
                .build();
    }

    static UserEntity mapToUserEntity(User user) {
        return UserEntity.builder()
                .nickname(user.getNickname())
                .socialId(user.getSocialId())
                .socialType(user.getSocialType())
                .role(user.getRole())
                .domain(user.getDomain())
                .email(user.getEmail())
                .id(user.getId())
                .withdrawAt(user.getWithdrawAt())
                .build();
    }
}

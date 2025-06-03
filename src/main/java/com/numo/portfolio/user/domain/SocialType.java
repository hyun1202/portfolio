package com.numo.portfolio.user.domain;

public enum SocialType {
    kakao,
//    naver,
    google,
//    github
    ;

    public static SocialType getType(String social) {
        return SocialType.valueOf(social);
    }
}

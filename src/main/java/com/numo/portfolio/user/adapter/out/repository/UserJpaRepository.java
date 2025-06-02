package com.numo.portfolio.user.adapter.out.repository;

import com.numo.portfolio.user.adapter.out.entity.SocialType;
import com.numo.portfolio.user.adapter.out.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType);
}

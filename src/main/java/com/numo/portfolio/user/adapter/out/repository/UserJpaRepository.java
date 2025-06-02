package com.numo.portfolio.user.adapter.out.repository;

import com.numo.portfolio.user.adapter.out.entity.SocialType;
import com.numo.portfolio.user.adapter.out.entity.UserEntity;
import com.numo.portfolio.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findBySocialId(String socialId);
    boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType);
}

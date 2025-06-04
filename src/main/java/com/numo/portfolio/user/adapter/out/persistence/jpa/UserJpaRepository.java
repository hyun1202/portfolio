package com.numo.portfolio.user.adapter.out.persistence.jpa;

import com.numo.portfolio.user.domain.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findBySocialId(String socialId);
    boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType);
    boolean existsByDomain(String domain);
}

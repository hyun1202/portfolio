package com.numo.portfolio.user.adapter.out.persistence;

import com.numo.portfolio.security.oauth2.info.OAuth2UserInfo;
import com.numo.portfolio.user.application.port.out.AddUserPort;
import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
import com.numo.portfolio.user.application.port.out.OAuth2UserPort;
import com.numo.portfolio.user.application.port.out.UpdateDomainPort;
import com.numo.portfolio.user.domain.User;
import com.numo.portfolio.user.adapter.out.persistence.jpa.UserEntity;
import com.numo.portfolio.user.adapter.out.persistence.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.numo.portfolio.user.adapter.out.persistence.UserMapper.mapToUser;
import static com.numo.portfolio.user.adapter.out.persistence.UserMapper.mapToUserEntity;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements AddUserPort, GetUserQueryPort, OAuth2UserPort, UpdateDomainPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User createUser(User user) {
        if (userJpaRepository.existsBySocialIdAndSocialType(user.getSocialId(), user.getSocialType())) {
            throw new IllegalArgumentException("이미 가입된 유저입니다.");
        }

        UserEntity userEntity = UserMapper.mapToUserEntity(user);
        UserEntity savedUserEntity = userJpaRepository.save(userEntity);

        return mapToUser(savedUserEntity);
    }

    @Override
    public User getUserBySocialId(String socialId) {
        UserEntity userEntity = userJpaRepository.findBySocialId(socialId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        return mapToUser(userEntity);
    }

    @Override
    public User getUserById(Long userId) {
        UserEntity userEntity = getUserEntity(userId);

        return mapToUser(userEntity);
    }

    @Override
    public User getOrSaveUser(OAuth2UserInfo oAuth2UserInfo) {
        UserEntity oauth2Entity = UserMapper.mapToUserEntity(oAuth2UserInfo);

        UserEntity userEntity = userJpaRepository.findBySocialId(oAuth2UserInfo.socialId())
                .orElseGet(() -> userJpaRepository.save(oauth2Entity));

        return mapToUser(userEntity);
    }

    @Override
    public Long updateDomain(Long userId, String domain) {
        if (userJpaRepository.existsByDomain(domain)) {
            throw new IllegalArgumentException("해당하는 도메인은 이미 존재합니다.");
        }

        UserEntity userEntity = getUserEntity(userId);
        userEntity.updateDomain(domain);

        return userEntity.getId();
    }

    private UserEntity getUserEntity(Long userId) {
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저를 찾을 수 없습니다.")
        );
        return userEntity;
    }
}

package com.numo.portfolio.user.adapter.out.persistence;

import com.numo.portfolio.comm.exception.CustomException;
import com.numo.portfolio.security.oauth2.info.OAuth2UserInfo;
import com.numo.portfolio.user.adapter.out.persistence.jpa.UserEntity;
import com.numo.portfolio.user.adapter.out.persistence.jpa.UserJpaRepository;
import com.numo.portfolio.user.application.port.out.AddUserPort;
import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
import com.numo.portfolio.user.application.port.out.OAuth2UserPort;
import com.numo.portfolio.user.application.port.out.UpdateUserPort;
import com.numo.portfolio.user.comm.exception.UserErrorCode;
import com.numo.portfolio.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.numo.portfolio.user.adapter.out.persistence.UserMapper.mapToUser;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements AddUserPort,
        GetUserQueryPort,
        OAuth2UserPort,
        UpdateUserPort
{
    private final UserJpaRepository userJpaRepository;

    @Override
    public User createUser(User user) {
        if (userJpaRepository.existsBySocialIdAndSocialType(user.getSocialId(), user.getSocialType())) {
            throw new CustomException(UserErrorCode.EXISTS_USER);
        }

        UserEntity userEntity = UserMapper.mapToUserEntity(user);
        UserEntity savedUserEntity = userJpaRepository.save(userEntity);

        return mapToUser(savedUserEntity);
    }

    @Override
    public User getUserBySocialId(String socialId) {
        UserEntity userEntity = userJpaRepository.findBySocialId(socialId).orElseThrow(
                () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage())
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
            throw new CustomException(UserErrorCode.EXISTS_DOMAIN);
        }

        UserEntity userEntity = getUserEntity(userId);
        userEntity.updateDomain(domain);

        return userEntity.getId();
    }

    @Override
    public Long updateUser(Long userId, String nickname) {
        UserEntity userEntity = getUserEntity(userId);
        userEntity.updateUser(nickname);
        return userEntity.getId();
    }

    @Override
    public void withdrawUser(Long userId) {
        UserEntity userEntity = getUserEntity(userId);
        userEntity.withdrawUser();
    }

    private UserEntity getUserEntity(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow(
                () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage())
        );
    }
}

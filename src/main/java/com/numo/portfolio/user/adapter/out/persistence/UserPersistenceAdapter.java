package com.numo.portfolio.user.adapter.out.persistence;

import com.numo.portfolio.user.application.port.out.AddUserPort;
import com.numo.portfolio.user.application.port.out.GetUserQueryPort;
import com.numo.portfolio.user.domain.User;
import com.numo.portfolio.user.adapter.out.persistence.entity.UserEntity;
import com.numo.portfolio.user.adapter.out.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements AddUserPort, GetUserQueryPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User createUser(User user) {
        System.out.println(user.getSocialType());
        if (userJpaRepository.existsBySocialIdAndSocialType(user.getSocialId(), user.getSocialType())) {
            throw new IllegalArgumentException("이미 가입된 유저입니다.");
        }

        UserEntity entity = UserEntity.builder()
                .nickname(user.getNickname())
                .socialId(user.getSocialId())
                .socialType(user.getSocialType())
                .build();

        UserEntity savedUserEntity = userJpaRepository.save(entity);

        return toUser(savedUserEntity);
    }

    @Override
    public User getUserBySocialId(String socialId) {
        UserEntity userEntity = userJpaRepository.findBySocialId(socialId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        return toUser(userEntity);
    }

    private User toUser(UserEntity userEntity) {
        return User.builder()
                .socialId(userEntity.getSocialId())
                .nickname(userEntity.getNickname())
                .id(userEntity.getId())
                .createdAt(userEntity.getCreatedAt())
                .modifiedAt(userEntity.getModifiedAt())
                .build();
    }

}

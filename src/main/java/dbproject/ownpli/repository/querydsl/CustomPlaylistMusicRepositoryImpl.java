package dbproject.ownpli.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.domain.PlaylistMusicEntity;
import dbproject.ownpli.domain.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dbproject.ownpli.domain.QPlaylistEntity.playlistEntity;
import static dbproject.ownpli.domain.QPlaylistMusicEntity.playlistMusicEntity;
import static dbproject.ownpli.domain.QUserEntity.userEntity;

@RequiredArgsConstructor
public class CustomPlaylistMusicRepositoryImpl implements CustomPlaylistMusicRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PlaylistMusicEntity> findAgeCompare(UserEntity user) {
        return jpaQueryFactory
                .select(playlistMusicEntity)
                .from(playlistEntity, userEntity, playlistMusicEntity)
                .where(
                        playlistEntity.userEntity.eq(userEntity),
                        playlistMusicEntity.playlistEntity.eq(playlistEntity),
                        betweenAge(user.getAge())
                ).fetch();
    }

    private BooleanExpression betweenAge(int age) {
        int age1 = age / 10 * 10;
        int age2 = age1 + 9;
        return userEntity.age.between(age1, age2);
    }
}

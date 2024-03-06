package dbproject.ownpli.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.domain.PlaylistMusic;
import dbproject.ownpli.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dbproject.ownpli.domain.QPlaylist.playlist;
import static dbproject.ownpli.domain.QPlaylistMusic.playlistMusic;
import static dbproject.ownpli.domain.QUser.user;

@RequiredArgsConstructor
public class CustomPlaylistMusicRepositoryImpl implements CustomPlaylistMusicRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PlaylistMusic> findAgeCompare(User userEntity) {
        return jpaQueryFactory
                .select(playlistMusic)
                .from(playlist, user, playlistMusic)
                .where(
                        playlist.user.eq(userEntity),
                        playlistMusic.playlist.eq(playlist),
                        betweenAge(userEntity.getAge())
                ).fetch();
    }

    private BooleanExpression betweenAge(int age) {
        int age1 = age / 10 * 10;
        int age2 = age1 + 9;
        return user.age.between(age1, age2);
    }
}

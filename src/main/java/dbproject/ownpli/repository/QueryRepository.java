package dbproject.ownpli.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static dbproject.ownpli.domain.QUserEntity.userEntity;
import static dbproject.ownpli.domain.music.QMoodEntity.moodEntity;
import static dbproject.ownpli.domain.music.QMusicEntity.musicEntity;
import static dbproject.ownpli.domain.music.QMusicMoodEntity.musicMoodEntity;
import static dbproject.ownpli.domain.playlist.QPlaylistEntity.playlistEntity;


@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MusicEntity> findDynamicQueryAdvance(List<String> likes,
                                                     List<String> hates,
                                                     List<Long> genre,
                                                     List<String> country,
                                                     List<String> year,
                                                     List<Long> mood) throws ParseException {
        return jpaQueryFactory
                .select(musicEntity)
                .from(musicEntity, musicMoodEntity).distinct()
                .where(musicMoodEntity.musicEntity.eq(musicEntity),
                        inSingers(likes),
                        notInSingers(hates),
                        inGenre(genre),
                        inCountry(country),
                        betweenDate(year),
                        inMood(mood)
                ).orderBy(musicEntity.musicId.asc()).fetch();
    }

    public List<String> findAgeCompare(UserEntity user) {
        return jpaQueryFactory
                .selectFrom(playlistEntity)
                .select(playlistEntity.playlistId)
                .from(playlistEntity, userEntity)
                .where(playlistEntity.userId.eq(userEntity.userId),
                        betweenAge(user.getAge())
                ).fetch();
    }

    private BooleanExpression inSingers(List<String> likes) {
        return likes != null ? musicEntity.singer.in(likes) : null;
    }

    private BooleanExpression notInSingers(List<String> hates) {
        return hates != null ? musicEntity.singer.notIn(hates) : null;
    }

    private BooleanExpression inGenre(List<Long> genre) {
        return genre != null ? musicEntity.genreNum.in(genre) : null;
    }

    private BooleanExpression inCountry(List<String> countries) {
        return countries != null ? musicEntity.country.in(countries) : null;
    }

    private BooleanExpression betweenDate(List<String> year) {
        return year != null ? musicEntity.date.year().in(year.stream().map(y -> Integer.parseInt(y.replace("'s", ""))).collect(Collectors.toList())) : null;
    }

    private BooleanExpression inMood(List<Long> mood) {
        return mood != null ? moodEntity.moodNum.in(mood) : null;
    }

    private BooleanExpression betweenAge(int age) {
        int age1 = age / 10 * 10;
        int age2 = age1 + 9;
        return userEntity.age.between(age1, age2);
    }

}

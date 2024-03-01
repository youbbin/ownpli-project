package dbproject.ownpli.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.GenreEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import dbproject.ownpli.dto.MusicListRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static dbproject.ownpli.domain.QUserEntity.userEntity;
import static dbproject.ownpli.domain.music.QMoodEntity.moodEntity;
import static dbproject.ownpli.domain.music.QMusicEntity.musicEntity;
import static dbproject.ownpli.domain.music.QMusicMoodEntity.musicMoodEntity;
import static dbproject.ownpli.domain.playlist.QPlaylistEntity.playlistEntity;
import static dbproject.ownpli.domain.playlist.QPlaylistMusicEntity.playlistMusicEntity;


@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MusicEntity> findDynamicQueryAdvance(MusicListRequest request) {
        return jpaQueryFactory
                .select(musicEntity)
                .from(musicEntity, musicMoodEntity, moodEntity).distinct()
                .where(musicMoodEntity.musicEntity.eq(musicEntity),
                        inSingers(request.getLikedSinger()),
                        notInSingers(request.getDislikedSinger()),
                        inGenre(request.getGenre()),
                        inCountry(request.getCountry()),
                        betweenDate(request.getYear()),
                        inMood(request.getMood())
                ).orderBy(musicEntity.musicId.asc()).fetch();
    }

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

    public List<MusicEntity> searchTitleAndSinger(String search) {
        List<String> searches = Arrays.stream(search.split(" ")).toList();

        return jpaQueryFactory
                .selectFrom(musicEntity)
                .where(inSingers(searches).or(inTitles(searches)))
                .fetch();
    }

    private BooleanExpression inTitles(List<String> titles) {
        return titles != null? musicEntity.title.in(titles) : null;
    }

    private BooleanExpression inSingers(List<String> likes) {
        return likes != null ? musicEntity.singer.in(likes) : null;
    }

    private BooleanExpression notInSingers(List<String> hates) {
        return hates != null ? musicEntity.singer.notIn(hates) : null;
    }

    private BooleanExpression inGenre(List<Long> genre) {
        return genre != null ? musicEntity.genreEntity.genreNum.in(genre) : null;
    }

    private BooleanExpression inCountry(List<String> countries) {
        return countries != null ? musicEntity.country.in(countries) : null;
    }

    private BooleanExpression betweenDate(List<String> year) {
        return year != null ? musicEntity.releaseDate.year().in(year.stream().map(y -> Integer.parseInt(y.replace("'s", ""))).collect(Collectors.toList())) : null;
    }

    private BooleanExpression inMood(List<Long> mood) {
        return mood != null ? musicMoodEntity.moodEntity.moodNum.in(mood) : null;
    }

    private BooleanExpression betweenAge(int age) {
        int age1 = age / 10 * 10;
        int age2 = age1 + 9;
        return userEntity.age.between(age1, age2);
    }

}

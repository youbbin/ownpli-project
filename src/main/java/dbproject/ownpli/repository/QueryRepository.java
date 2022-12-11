package dbproject.ownpli.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static dbproject.ownpli.domain.QUserEntity.userEntity;
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
            .selectFrom(musicEntity)
            .select(musicEntity)
            .from(musicEntity, musicMoodEntity)
            .where(musicMoodEntity.musicId.eq(musicEntity.musicId),
                inSingers(likes),
                notInSingers(hates).not(),
                inGenre(genre),
                inCountry(country),
                betweenDate(year),
                inMood(mood)
            ).fetch();
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

    private BooleanBuilder inSingers(List<String> likes) {
        if (likes == null) {
            return null;
        }

        log.info("singerLikeCount={}",likes.size());

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String like : likes){
            log.info("singerLike = {}", like);
            booleanBuilder.or(musicEntity.singer.contains(like));
        }

        return booleanBuilder;
    }

    private BooleanBuilder notInSingers(List<String> hates) {
        if (hates == null) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String hate : hates){
            log.info("singerHate={}", hate);
            booleanBuilder.or(musicEntity.singer.contains(hate));
        }

        return booleanBuilder;
    }

    private BooleanBuilder inGenre(List<Long> genre) {
        if (genre == null) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (Long g : genre){
            log.info("genre={}", g);
            booleanBuilder.or(musicEntity.genreNum.eq(g));
        }

        return booleanBuilder;
    }

    private BooleanBuilder inCountry(List<String> ctry) {
        if (ctry == null) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String c : ctry){
            log.info("country={}", c);
            booleanBuilder.or(musicEntity.country.eq(c));
        }

        return booleanBuilder;
    }

    private BooleanBuilder betweenDate(List<String> year) throws ParseException {
        if(year == null) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;


        for (String y : year){
            log.info("year={}",y);
            String a = y.replace("'s", "");
            log.info("year={}",a);
            date1 = new Date(format.parse(a + "-01-01").getTime());
            date2 = new Date(format.parse((Integer.parseInt(a) + 9) + "-12-31").getTime());

            booleanBuilder.or(musicEntity.date.between(date1, date2));
        }

        return booleanBuilder;
    }

    private BooleanBuilder inMood(List<Long> mood) {
        if (mood == null) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (Long m : mood){
            log.info("mood={}", m);
            booleanBuilder.or(musicMoodEntity.moodNum.eq(m));
        }

        return booleanBuilder;
    }

    private BooleanExpression betweenAge(int age) {
        int age1 = age / 10 * 10;
        int age2 = age1 + 9;
        return userEntity.age.between(age1, age2);
    }

}

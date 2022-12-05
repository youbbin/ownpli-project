package dbproject.ownpli.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.domain.music.MusicEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static dbproject.ownpli.domain.music.QMusicEntity.musicEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MusicEntity> findDynamicQueryAdvance(List<String> likes,
                                                     List<String> hates,
                                                     List<Long> genre,
                                                     List<String> country,
                                                     List<String> year) throws ParseException {
        return jpaQueryFactory
            .selectFrom(musicEntity)
            .where(inSingers(likes),
                notInSingers(hates),
                inGenre(genre),
                inCountry(country),
                betweenDate(year)
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
            booleanBuilder.or(musicEntity.singer.eq(hate).not());
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

}

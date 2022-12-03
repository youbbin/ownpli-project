package dbproject.ownpli.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.domain.music.MusicEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static dbproject.ownpli.domain.music.QMusicEntity.musicEntity;

@Repository
@RequiredArgsConstructor
public class QueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MusicEntity> findDynamicQueryAdvance(List<String> likes,
                                                     List<String> hates,
                                                     List<Long> genre,
                                                     List<String> Langs,
                                                     List<String> year) throws ParseException {
        return jpaQueryFactory
            .selectFrom(musicEntity)
            .where(inSingers(likes),
                notInSingers(hates),
                inGenre(genre),
                eqLangs(Langs),
                betweenDate(year)
//                eqGender(Gender)
            ).fetch();
    }

    private BooleanBuilder inSingers(List<String> likes) {
        if (likes.isEmpty()) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String like : likes){
            booleanBuilder.or(musicEntity.singer.eq(like));
        }

        return booleanBuilder;
    }

    private BooleanBuilder notInSingers(List<String> hates) {
        if (hates.isEmpty()) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String hate : hates){
            booleanBuilder.or(musicEntity.singer.eq(hate).not());
        }

        return booleanBuilder;
    }

    private BooleanBuilder inGenre(List<Long> genre) {
        if (genre.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (Long g : genre){
            booleanBuilder.or(musicEntity.genreNum.eq(g).not());
        }

        return booleanBuilder;
    }

    private BooleanBuilder eqLangs(List<String> langs) {
        if (langs.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String l : langs){
            booleanBuilder.or(musicEntity.country.eq(l).not());
        }

        return booleanBuilder;
    }

    private BooleanBuilder betweenDate(List<String> year) throws ParseException {
        if(year.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;

        for (String y : year){
            date1 = (Date) format.parse(year + "-01-01");
            date2 = (Date) format.parse(year + "-12-31");

            booleanBuilder.or(musicEntity.date.between(date1, date2));
        }

        return booleanBuilder;
    }
//
//    private BooleanBuilder eqGender(List<Integer> gender) {
//        if (gender.isEmpty()) {
//            return null;
//        }
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        for (Integer g : gender){
//            booleanBuilder.or(musicEntity.gen);
//        }
//
//        return booleanBuilder;
//    }

}

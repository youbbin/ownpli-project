package dbproject.ownpli.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.controller.dto.music.MusicListRequest;
import dbproject.ownpli.domain.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static dbproject.ownpli.domain.QMusic.music;
import static dbproject.ownpli.domain.QMusicMood.musicMood;

@RequiredArgsConstructor
public class CustomMusicRepositoryImpl implements CustomMusicRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Music> findDynamicQueryAdvance(MusicListRequest request, Pageable pageable) {
        List<Music> result = jpaQueryFactory
                .select(music)
                .from(music, musicMood).distinct()
                .where(musicMood.music.eq(music),
                        inSingers(request.getLikedSinger()),
                        notInSingers(request.getDislikedSinger()),
                        inGenre(request.getGenre()),
                        inCountry(request.getCountry()),
                        betweenDate(request.getYear()),
                        inMood(request.getMood())
                ).orderBy(music.musicId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(result, pageable, getCount(request)::fetchOne);
    }

    private JPAQuery<Long> getCount(MusicListRequest request) {
        return jpaQueryFactory
                .select(music.count())
                .from(music, musicMood).distinct()
                .where(musicMood.music.eq(music),
                        inSingers(request.getLikedSinger()),
                        notInSingers(request.getDislikedSinger()),
                        inGenre(request.getGenre()),
                        inCountry(request.getCountry()),
                        betweenDate(request.getYear()),
                        inMood(request.getMood())
                );
    }

    @Override
    public List<Music> searchTitleAndSinger(String search) {
        List<String> searches = Arrays.stream(search.split(" ")).collect(Collectors.toList());

        return jpaQueryFactory
                .selectFrom(music)
                .where(inSingers(searches).or(inTitles(searches)))
                .fetch();
    }

    private BooleanExpression inSingers(List<String> likes) {
        return likes != null ? music.singer.in(likes) : null;
    }

    private BooleanExpression notInSingers(List<String> hates) {
        return hates != null ? music.singer.notIn(hates) : null;
    }

    private BooleanExpression inTitles(List<String> titles) {
        return titles != null? music.title.in(titles) : null;
    }

    private BooleanExpression inGenre(List<Long> genre) {
        return genre != null ? music.genre.genreNum.in(genre) : null;
    }

    private BooleanExpression inCountry(List<String> countries) {
        return countries != null ? music.country.in(countries) : null;
    }

    private BooleanExpression betweenDate(List<String> year) {
        return year != null ? music.releaseDate.year().in(year.stream().map(y -> Integer.parseInt(y.replace("'s", ""))).collect(Collectors.toList())) : null;
    }

    private BooleanExpression inMood(List<Long> mood) {
        return mood != null ? musicMood.mood.moodNum.in(mood) : null;
    }

}

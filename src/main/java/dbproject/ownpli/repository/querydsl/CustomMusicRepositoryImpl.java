package dbproject.ownpli.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbproject.ownpli.controller.dto.music.MusicListRequest;
import dbproject.ownpli.domain.MusicEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static dbproject.ownpli.domain.QMoodEntity.moodEntity;
import static dbproject.ownpli.domain.QMusicEntity.musicEntity;
import static dbproject.ownpli.domain.QMusicMoodEntity.musicMoodEntity;

@RequiredArgsConstructor
public class CustomMusicRepositoryImpl implements CustomMusicRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<MusicEntity> findDynamicQueryAdvance(MusicListRequest request, Pageable pageable) {
        List<MusicEntity> result = jpaQueryFactory
                .select(musicEntity)
                .from(musicEntity, musicMoodEntity, moodEntity).distinct()
                .where(musicMoodEntity.musicEntity.eq(musicEntity),
                        inSingers(request.getLikedSinger()),
                        notInSingers(request.getDislikedSinger()),
                        inGenre(request.getGenre()),
                        inCountry(request.getCountry()),
                        betweenDate(request.getYear()),
                        inMood(request.getMood())
                ).orderBy(musicEntity.musicId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(result, pageable, getCount(request)::fetchOne);
    }

    private JPAQuery<Long> getCount(MusicListRequest request) {
        return jpaQueryFactory
                .select(musicEntity.count())
                .from(musicEntity, musicMoodEntity, moodEntity).distinct()
                .where(musicMoodEntity.musicEntity.eq(musicEntity),
                        inSingers(request.getLikedSinger()),
                        notInSingers(request.getDislikedSinger()),
                        inGenre(request.getGenre()),
                        inCountry(request.getCountry()),
                        betweenDate(request.getYear()),
                        inMood(request.getMood())
                );
    }

    @Override
    public List<MusicEntity> searchTitleAndSinger(String search) {
        List<String> searches = Arrays.stream(search.split(" ")).toList();

        return jpaQueryFactory
                .selectFrom(musicEntity)
                .where(inSingers(searches).or(inTitles(searches)))
                .fetch();
    }

    private BooleanExpression inSingers(List<String> likes) {
        return likes != null ? musicEntity.singer.in(likes) : null;
    }

    private BooleanExpression notInSingers(List<String> hates) {
        return hates != null ? musicEntity.singer.notIn(hates) : null;
    }

    private BooleanExpression inTitles(List<String> titles) {
        return titles != null? musicEntity.title.in(titles) : null;
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

}

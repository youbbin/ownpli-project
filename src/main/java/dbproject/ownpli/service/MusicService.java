package dbproject.ownpli.service;

import dbproject.ownpli.domain.music.GenreEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;
    private final MusicMoodRepository musicMoodRepository;
    private final GenreRepository genreRepository;
    private final MoodRepository moodRepository;
    private final SingerRepository singerRepository;

    /**
     * 모든 음악리스트 찾기
     * @return
     */
    public List<MusicEntity> findAllMusics() {
        return musicRepository.findAll();
    }

    /**
     *  singerId로 음악 리스트 출력
     * @param singerId
     * @return
     */
    public List<MusicEntity> findBySingerId(String singerId) {
        return musicRepository.findBySingerId(singerId);
    }

    /**
     * 가수 이름으로 가수의 음악 리스트 조회
     * @param singerName
     * @return
     */
    public List<MusicEntity> findBySingerName(String singerName) {
        String id = singerRepository.findBySingerName(singerName).getSingerId();
        return musicRepository.findBySingerId(id);
    }

    /**
     * 단일 음악 장르 조회
     * @param id
     * @return
     */
    public GenreEntity findGenreByMusicId(String id) {
        Long genreId = musicRepository.findGenreByMusicId(id);
        return genreRepository.findById(genreId).get();
    }

//    /**
//     * 음악 아이디로 mood 조회
//     * @param id
//     * @return
//     */
//    public List<MoodEntity> findMoodByMusicId(String id) {
//        List<Long> moodIds = musicMoodRepository.findMoodsByMusicId(id);
//        return moodRepository.findByIds(moodIds);
//    }

}

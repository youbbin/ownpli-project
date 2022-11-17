package dbproject.ownpli.service;

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

    /**
     * 모든 음악리스트 찾기
     * @return
     */
    public List<MusicEntity> findAllMusics() {
        return musicRepository.findAll();
    }

    /**
     * 음악 이름으로 음악 리스트 찾기
     * @param title
     * @return
     */
    public List<MusicEntity> findByTitle(String title) {
        return musicRepository.findByTitle(title);
    }

    /**
     * 음악 이름으로 검색
     * @param title
     * @return
     */
    public List<MusicEntity> findByTitleContain(String title) {
        return musicRepository.findByTitleContainingIgnoreCase(title);
    }


    /**
     * 가수 이름으로 검색
     * @param singer
     * @return
     */
    public List<MusicEntity> findBySingerContain(String singer) {
        return musicRepository.findBySingerContainingIgnoreCase(singer);
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

package dbproject.ownpli.service;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;
    private final MusicMoodRepository musicMoodRepository;
    private final MusicLikeRepository musicLikeRepository;
    private final GenreRepository genreRepository;
    private final MoodRepository moodRepository;

    /**
     * 모든 음악리스트 찾기
     * @return
     */
    public List<MusicEntity> findAllMusics() {
        return musicRepository.findAll();
    }

    public MusicEntity findByMusicId(String musicId) {
        return musicRepository.findById(musicId).get();
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
     * 음악 이름으로 음악 리스트 검색
     * @param title
     * @return
     */
    public List<MusicEntity> findByTitleContain(String title) {
        return musicRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * 가수 이름으로 음악 리스트 검색
     * @param singer
     * @return
     */
    public List<MusicEntity> findBySingerContain(String singer) {
        return musicRepository.findBySingerContainingIgnoreCase(singer);
    }

    /**
     * 사용자가 좋아요한 음악 리스트 출력
     * @param userId
     * @return
     */
    public List<MusicEntity> findMusicListByUserId(String userId) {
        List<String> musicIds = musicLikeRepository.findByUserId(userId);
        return musicRepository.findByMusicId(musicIds);
    }

    /**
     * 장르 아이디로 장르 이름 출력
     * @param genreId
     * @return String
     */
    public String findByGenreId(Long genreId) {
        return genreRepository.findById(genreId).get().getGenreName();
    }

    /**
     * musicId로 txt파일에서 가사 불러오기
     * @param musicId
     * @return Model
     * @throws IOException
     */
    public String readLirics(String musicId) throws IOException {
        //파일 읽기
        BufferedReader br = new BufferedReader(new FileReader(musicRepository.findById(musicId).get().getLiricsFile()));
        String line ="", result = "";

        //한 줄 씩 읽은 내용 쓰기
        while( (line=br.readLine()) != null ) {
            result += line;
            result += "\n";
        }

        return result;
    }

    /**
     * 음악 아이디로 mood 조회
     * @param musicId
     * @return List[String]
     */
    public List<String> findMoodByMusicId(String musicId) {
        List<Long> byMusicId = musicMoodRepository.findByMusicId(musicId);
        return moodRepository.findByIds(byMusicId);
    }

}

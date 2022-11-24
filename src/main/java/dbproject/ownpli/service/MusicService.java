package dbproject.ownpli.service;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public List<MusicDTO> findAllMusics() {
        List<MusicEntity> all = musicRepository.findAll();
        List<MusicDTO> models = new ArrayList<>();

        for(int i = 0; i < all.size(); i++) {
            models.add(findMusicInfo(all.get(i).getMusicId()));
        }
        return models;
    }

    /**
     * 음악 아이디로 음악 엔티티 찾기
     * @param musicId
     * @return
     */
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
     * 단일 음악 정보 보내기
     * @param musicId
     * @return Model
     * @throws IOException
     */
    public MusicDTO findMusicInfo(String musicId) {
        log.info("musicId = " + musicId);
        MusicEntity byMusicId = findByMusicId(musicId);
        List<String> moodByMusicId = findMoodByMusicId(musicId);
        String byGenreId = findByGenreId(byMusicId.getGenreNum());

        Optional<Long> aLong = musicLikeRepository.countByMusicId(musicId);
        Long likes = Long.valueOf(0);
        if (!aLong.isEmpty()) likes = aLong.get();

        String inputFile = byMusicId.getImageFile();
        Path path = new File(inputFile).toPath();
        FileSystemResource resource = new FileSystemResource(path);

        log.info("byMusicId.getMusicId = " + byMusicId.getMusicId());

        return MusicDTO.from(byMusicId, byGenreId, moodByMusicId, likes, resource);
    }

    public List<MusicDTO> findMusicInfosByPlaylist(List<String> musicIds) {
        List<MusicDTO> arr = new ArrayList<>();


        for(int i = 0; i < musicIds.size(); i++) {
            arr.add(findMusicInfo(musicIds.get(i)));
        }

        return arr;
    }

    /**
     * 장르 아이디로 장르 이름 출력
     * @param genreId
     * @return String
     */
    public String findByGenreId(Long genreId) {
        return genreRepository.findById(genreId).get().getGenre();
    }

    /**
     * musicId로 txt파일에서 가사 불러오기
     * @param musicId
     * @return Model
     * @throws IOException
     */
    public String readLirics(String musicId) throws IOException {
        //파일 읽기
        BufferedReader br = new BufferedReader(new FileReader(musicRepository.findById(musicId).get().getLyricsFile()));
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

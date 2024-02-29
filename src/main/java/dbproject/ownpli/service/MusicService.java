package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.GenreEntity;
import dbproject.ownpli.domain.music.MoodEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicLikeEntity;
import dbproject.ownpli.dto.MusicListRequest;
import dbproject.ownpli.dto.MusicResponse;
import dbproject.ownpli.dto.MusicSearchListResponse;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;
    private final MusicLikeRepository musicLikeRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final QueryRepository queryRepository;

    public List<String> findSingerList() {
        return musicRepository.findSingers();
    }

    private boolean validateDuplicateLikes(UserEntity user, MusicEntity music) {
        return musicLikeRepository.existsByMusicEntityAndUserEntity(music, user);
    }

    public String musicLikeSetting(String userId, String musicId) {

        MusicEntity musicEntity = musicRepository.findById(musicId)
                .orElseThrow(() -> new NullPointerException("id 없음"));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("id 없음"));

        if (validateDuplicateLikes(userEntity, musicEntity)) {
            musicLikeRepository.deleteByMusicEntityAndUserEntity(musicEntity, userEntity);
        }
        else {
            musicLikeRepository.save(MusicLikeEntity.of(userEntity, musicEntity));
        }

        return musicId;
    }

    /**
     * MusicEntity 리스트를 MusicDTO로 변환
     *
     * @param musicEntities
     * @return
     */
    public List<MusicResponse> musicEntitiesToMusicDTO(List<MusicEntity> musicEntities) {
        List<MusicResponse> models = new ArrayList<>();

        for (int i = 0; i < musicEntities.size(); i++) {
            models.add(findMusicInfo(musicEntities.get(i).getMusicId()));
        }
        return models;
    }


    /**
     * 음악 이름으로 음악 아이디들 찾기
     *
     * @param title
     * @return
     */
    public List<String> findByTitle(List<String> title) {
        return musicRepository.findMusicIdsByTitle(title);
    }

    /**
     * 음악 이름으로 음악 아이디들 찾기
     *
     * @param title
     * @return
     */
    public MusicEntity findOneMusicIdByTitle(String title) {
        return musicRepository.findMusicEntityByTitleContainingIgnoreCase(title)
                .orElseThrow(() -> new NullPointerException("일치하는 제목이 없습니다."));
    }

    /**
     * 음악 이름으로 음악 리스트 검색
     *
     * @param title
     * @return
     */
    public List<MusicEntity> findByTitleContain(String title) {
        return musicRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * 가수 이름으로 음악 리스트 검색
     *
     * @param singer
     * @return
     */
    public List<MusicEntity> findBySingerContain(String singer) {
        return musicRepository.findBySingerContainingIgnoreCase(singer);
    }

    /**
     * 단일 음악 정보 보내기
     *
     * @param musicId
     * @return MusicResponse
     * @throws IOException
     */
    public MusicResponse findMusicInfo(String musicId) {
        log.info("musicId = " + musicId);
        MusicEntity musicEntity = musicRepository.findById(musicId).orElseThrow(() -> new NullPointerException("없음"));

        Long likes = musicLikeRepository.countByMusicEntity(musicEntity);


        return MusicResponse.ofMusic(musicEntity, likes);
    }

    public List<MusicSearchListResponse> searchByCondition(MusicListRequest request) {
        List<GenreEntity> genreEntities = genreRepository.findAllById(request.getGenre());

        return queryRepository.findDynamicQueryAdvance(request, genreEntities).stream()
                .map(MusicSearchListResponse::of)
                .collect(Collectors.toList());
    }


    /**
     * 플레이리스트에서 뮤직 정보 가져오기
     *
     * @param musicIds
     * @return
     */
    public List<MusicResponse> findMusicInfosByPlaylist(List<String> musicIds) {
        List<MusicResponse> arr = new ArrayList<>();

        for (int i = 0; i < musicIds.size(); i++) {
            arr.add(findMusicInfo(musicIds.get(i)));
        }

        return arr;
    }

    /**
     * musicId로 txt파일에서 가사 불러오기
     *
     * @param musicId
     * @return Model
     * @throws IOException
     */
    public String readLyrics(String musicId) throws IOException {
        String path = musicRepository.findById(musicId).get().getLyricsFile();
        //D to C
        path = path.replaceFirst("D", "C");
        //파일 읽기
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = "", result = "";

        //한 줄 씩 읽은 내용 쓰기
        while ((line = br.readLine()) != null) {
            result += line;
            result += "\n";
        }

        return result;
    }

}

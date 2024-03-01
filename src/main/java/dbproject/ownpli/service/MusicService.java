package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.MusicEntity;
import dbproject.ownpli.domain.MusicLikeEntity;
import dbproject.ownpli.controller.dto.music.MusicListRequest;
import dbproject.ownpli.controller.dto.music.MusicResponse;
import dbproject.ownpli.controller.dto.music.MusicSearchListResponse;
import dbproject.ownpli.controller.dto.music.SingerListResponse;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicService {

    private final MusicRepository musicRepository;
    private final MusicLikeRepository musicLikeRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final QueryRepository queryRepository;

    public SingerListResponse findSingerList() {
        List<String> collect = musicRepository.findAll().stream()
                .map(MusicEntity::getSinger)
                .distinct()
                .collect(Collectors.toList());

        return SingerListResponse.of(collect);
    }

    private boolean validateDuplicateLikes(UserEntity user, MusicEntity music) {
        return musicLikeRepository.existsByMusicEntityAndUserEntity(music, user);
    }

    @Transactional
    public void musicLikeSetting(String userId, String musicId) {

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

    }

    public List<MusicResponse> searchSingerAndTitle(String search) {
        return queryRepository.searchTitleAndSinger(search).stream()
                .map(musicEntity -> MusicResponse.ofMusic(
                        musicEntity,
                        musicLikeRepository.countByMusicEntity(musicEntity)
                ))
                .collect(Collectors.toList());
    }

    public MusicResponse findMusicInfo(String musicId) {
        log.info("musicId = " + musicId);
        MusicEntity musicEntity = musicRepository.findById(musicId)
                .orElseThrow(() -> new NullPointerException("없음"));

        Long likes = musicLikeRepository.countByMusicEntity(musicEntity);

        return MusicResponse.ofMusic(musicEntity, likes);
    }

    public Page<MusicSearchListResponse> searchByCondition(MusicListRequest request, Pageable pageable) {
        return queryRepository.findDynamicQueryAdvance(request, pageable)
                .map(MusicSearchListResponse::of);
    }

    public String readLyrics(String musicId) throws IOException {
        String path = musicRepository.findById(musicId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."))
                .getLyricsFile();

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

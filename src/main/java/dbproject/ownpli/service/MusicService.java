package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.GenreEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicLikeEntity;
import dbproject.ownpli.dto.MusicListRequest;
import dbproject.ownpli.dto.MusicResponse;
import dbproject.ownpli.dto.MusicSearchListResponse;
import dbproject.ownpli.dto.SingerListResponse;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void musicLikeSetting(String userId, Long musicId) {

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

    public MusicResponse findMusicInfo(Long musicId) {
        log.info("musicId = " + musicId);
        MusicEntity musicEntity = musicRepository.findById(musicId)
                .orElseThrow(() -> new NullPointerException("없음"));

        Long likes = musicLikeRepository.countByMusicEntity(musicEntity);

        return MusicResponse.ofMusic(musicEntity, likes);
    }

    public List<MusicSearchListResponse> searchByCondition(MusicListRequest request) {
        List<GenreEntity> genreEntities = genreRepository.findAllById(request.getGenre());

        return queryRepository.findDynamicQueryAdvance(request, genreEntities).stream()
                .map(MusicSearchListResponse::of)
                .collect(Collectors.toList());
    }

    public String readLyrics(Long musicId) throws IOException {
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

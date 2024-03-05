package dbproject.ownpli.service;

import dbproject.ownpli.domain.User;
import dbproject.ownpli.domain.Music;
import dbproject.ownpli.domain.MusicLike;
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
    private final UserRepository userRepository;

    public SingerListResponse findSingerList() {
        List<String> collect = musicRepository.findAll().stream()
                .map(Music::getSinger)
                .distinct()
                .collect(Collectors.toList());

        return SingerListResponse.of(collect);
    }

    private boolean validateDuplicateLikes(User user, Music music) {
        return musicLikeRepository.existsByMusicAndUser(music, user);
    }

    @Transactional
    public void musicLikeSetting(String userId, String musicId) {

        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new NullPointerException("id 없음"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("id 없음"));

        if (validateDuplicateLikes(user, music)) {
            musicLikeRepository.deleteByMusicAndUser(music, user);
        }
        else {
            musicLikeRepository.save(MusicLike.of(user, music));
        }

    }

    public List<MusicResponse> searchSingerAndTitle(String search) {
        return musicRepository.searchTitleAndSinger(search).stream()
                .map(musicEntity -> MusicResponse.ofMusic(
                        musicEntity,
                        musicLikeRepository.countByMusic(musicEntity)
                ))
                .collect(Collectors.toList());
    }

    public MusicResponse findMusicInfo(String musicId) {
        log.info("musicId = " + musicId);
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new NullPointerException("없음"));

        Long likes = musicLikeRepository.countByMusic(music);

        return MusicResponse.ofMusic(music, likes);
    }

    public Page<MusicSearchListResponse> searchByCondition(MusicListRequest request, Pageable pageable) {
        return musicRepository.findDynamicQueryAdvance(request, pageable)
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

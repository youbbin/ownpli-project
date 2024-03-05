package dbproject.ownpli.service;

import dbproject.ownpli.domain.User;
import dbproject.ownpli.domain.Mood;
import dbproject.ownpli.domain.Music;
import dbproject.ownpli.domain.MusicLike;
import dbproject.ownpli.domain.PlaylistMusic;
import dbproject.ownpli.controller.dto.home.HomeMusicListResponse;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HomeService {
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final MusicLikeRepository musicLikeRepository;
    private final MoodRepository moodRepository;
    private final MusicMoodRepository musicMoodRepository;
    private final UserRepository userRepository;

    public List<HomeMusicListResponse> findNewSongs() {

        return musicRepository.findAll().stream()
                .sorted(Comparator.comparing(Music::getReleaseDate, Comparator.reverseOrder()))
                .limit(10)
                .map(HomeMusicListResponse::ofMusic)
                .collect(Collectors.toList());
    }

    public List<HomeMusicListResponse> findTop10Musics() {

        Map<Music, Long> musicEntityLongMap = playlistMusicRepository.findAll().stream()
                .collect(Collectors.groupingBy(PlaylistMusic::getMusic, Collectors.counting()));

        return buildResponse(musicEntityLongMap);
    }

    public List<HomeMusicListResponse> findTop10LikeList() {

        Map<Music, Long> musicEntityLongMap = musicLikeRepository.findAll().stream()
                .collect(Collectors.groupingBy(MusicLike::getMusic, Collectors.counting()));

        return buildResponse(musicEntityLongMap);
    }


    public List<HomeMusicListResponse> getAgeList(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        Map<Music, Long> musicEntityLongMap = playlistMusicRepository.findAgeCompare(user).stream()
                .collect(Collectors.groupingBy(PlaylistMusic::getMusic, Collectors.counting()));

        return buildResponse(musicEntityLongMap);
    }

    private List<HomeMusicListResponse> buildResponse(Map<Music, Long> map) {
        return map
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .distinct()
                .limit(10)
                .map(entry -> HomeMusicListResponse.ofMusic(entry.getKey()))
                .collect(Collectors.toList());
    }

    public List<HomeMusicListResponse> mood5List() {
        Mood mood;
        if (LocalDate.now().getMonthValue() == 12) {
            mood = moodRepository.findMoodEntityByMood("캐롤");
        } else
            mood = moodRepository.findById((long) ((Math.random() * 10000) % 22))
                    .orElseThrow(() -> new NullPointerException("id 없음"));

        return musicMoodRepository.findByMood(mood).stream()
                .limit(5)
                .map(HomeMusicListResponse::ofMusicMood)
                .collect(Collectors.toList());
    }

}

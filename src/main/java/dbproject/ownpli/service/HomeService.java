package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.MoodEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicLikeEntity;
import dbproject.ownpli.domain.music.MusicMoodEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import dbproject.ownpli.dto.HomeMusicListResponse;
import dbproject.ownpli.dto.MusicResponse;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final MusicService musicService;
    private final MusicLikeRepository musicLikeRepository;
    private final MoodRepository moodRepository;
    private final MusicMoodRepository musicMoodRepository;
    private final QueryRepository queryRepository;
    private final UserRepository userRepository;

    public List<HomeMusicListResponse> findNewSongs() {

        return musicRepository.findAll().stream()
                .sorted(Comparator.comparing(MusicEntity::getDate, Comparator.reverseOrder()))
                .limit(10)
                .map(HomeMusicListResponse::ofMusic)
                .collect(Collectors.toList());
    }

    public List<HomeMusicListResponse> findTop10Musics() {

        Map<MusicEntity, Long> musicEntityLongMap = playlistMusicRepository.findAll().stream()
                .collect(Collectors.groupingBy(PlaylistMusicEntity::getMusicEntity, Collectors.counting()));

        return buildResponse(musicEntityLongMap);
    }

    public List<HomeMusicListResponse> findTop10LikeList() {

        Map<MusicEntity, Long> musicEntityLongMap = musicLikeRepository.findAll().stream()
                .collect(Collectors.groupingBy(MusicLikeEntity::getMusicEntity, Collectors.counting()));

        return buildResponse(musicEntityLongMap);
    }


    public List<HomeMusicListResponse> getAgeList(String userId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        Map<MusicEntity, Long> musicEntityLongMap = queryRepository.findAgeCompare(userEntity).stream()
                .collect(Collectors.groupingBy(PlaylistMusicEntity::getMusicEntity, Collectors.counting()));

        return buildResponse(musicEntityLongMap);
    }

    private List<HomeMusicListResponse> buildResponse(Map<MusicEntity, Long> map) {
        return map
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .distinct()
                .limit(10)
                .map(entry -> HomeMusicListResponse.ofMusic(entry.getKey()))
                .collect(Collectors.toList());
    }

    public List<MusicResponse> mood5List() {
        MoodEntity moodEntity;
        if (LocalDate.now().getMonthValue() == 12) {
            moodEntity = moodRepository.findMoodEntityByMood("캐롤");
        } else
            moodEntity = moodRepository.findById((long) ((Math.random() * 10000) % 22))
                    .orElseThrow(() -> new NullPointerException("id 없음"));

        return musicMoodRepository.findByMoodEntity(moodEntity).stream()
                .map(musicMoodEntity -> MusicResponse.ofMusicMood(
                        musicMoodEntity,
                        musicLikeRepository.countByMusicEntity(musicMoodEntity.getMusicEntity())
                ))
                .collect(Collectors.toList());
    }

}

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
                .limit(10)
                .sorted(Comparator.comparing(MusicEntity::getDate, Comparator.reverseOrder()))
                .map(HomeMusicListResponse::ofMusic)
                .collect(Collectors.toList());
    }

    /**
     * playlist 많이 담은 순으로 음악 보내기
     * @return
     */
    public List<MusicResponse> findTop10Musics() {
        Optional<List<String>> distinctMusicIdOptional = playlistMusicRepository.findDistinctMusicId();

//        if(distinctMusicIdOptional.isEmpty() || distinctMusicIdOptional.get().size() < 10) {
//            List<MusicResponse> musicDTOList = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                musicDTOList.add(musicService.findMusicInfo(musicRepository.findAll().get(i).getMusicId()));
//            }
//            return musicDTOList;
//        }

        return musicService.findMusicInfosByPlaylist(distinctMusicIdOptional.get()).subList(0, 10);
    }

    public List<HomeMusicListResponse> findTop10LikeList() {

        Map<MusicEntity, Long> musicEntityLongMap = musicLikeRepository.findAll().stream()
                .collect(Collectors.groupingBy(MusicLikeEntity::getMusicEntity, Collectors.counting()));

        return musicEntityLongMap
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(entry -> HomeMusicListResponse.ofMusic(entry.getKey()))
                .collect(Collectors.toList());
    }


    public List<HomeMusicListResponse> getAgeList(String userId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        Map<MusicEntity, Long> musicEntityLongMap = queryRepository.findAgeCompare(userEntity).stream()
                .collect(Collectors.groupingBy(PlaylistMusicEntity::getMusicEntity, Collectors.counting()));

        return musicEntityLongMap
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(entry -> HomeMusicListResponse.ofMusic(entry.getKey()))
                .collect(Collectors.toList());
    }

    public List<MusicResponse> mood5List() {
        MoodEntity moodEntity;
        if(LocalDate.now().getMonthValue() == 12) {
            moodEntity = moodRepository.findMoodEntityByMood("캐롤");
        }
        else
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

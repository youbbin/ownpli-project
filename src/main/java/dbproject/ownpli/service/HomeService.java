package dbproject.ownpli.service;

import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final MusicService musicService;
    private final MusicLikeRepository musicLikeRepository;
    private final MoodRepository moodRepository;
    private final MusicMoodRepository musicMoodRepository;
    private final UserService userService;
    private final QueryRepository queryRepository;

    public List<MusicDTO> findNewSongs() {
        List<String> musicIdsOrderByYear = musicRepository.findMusicIdsOrderByYear();
        return musicService.findMusicInfosByPlaylist(musicIdsOrderByYear).subList(0, 10);
    }

    /**
     * playlist 많이 담은 순으로 음악 보내기
     * @return
     */
    public List<MusicDTO> findTop10Musics() {
        Optional<List<String>> distinctMusicIdOptional = playlistMusicRepository.findDistinctMusicId();

        if(distinctMusicIdOptional.isEmpty() || distinctMusicIdOptional.get().size() < 10) {
            List<MusicDTO> musicDTOList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                musicDTOList.add(musicService.findMusicInfo(musicRepository.findAll().get(i).getMusicId()));
            }
            return musicDTOList;
        }

        return musicService.findMusicInfosByPlaylist(distinctMusicIdOptional.get()).subList(0, 10);
    }

    /**
     * 좋아요 많이 받은 순으로 출력
     * @return
     */
    public List<MusicDTO> findTop10LikeList() {
        Optional<List<String>> musicIds = musicLikeRepository.findMusicIds();

        if(musicIds == null || musicIds.get().size() < 10) {
            List<MusicDTO> musicDTOList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                musicDTOList.add(musicService.findMusicInfo(musicRepository.findAll().get(i).getMusicId()));
            }
            return musicDTOList;
        }

        return musicService.findMusicInfosByPlaylist(musicIds.get()).subList(0, 10);
    }


    public List<MusicDTO> ageList(String userId) {
        List<String> ageCompare = queryRepository.findAgeCompare(userService.findByUserId(userId));
        List<String> byPlaylistId = playlistMusicRepository.findByPlaylistId(ageCompare);
        List<MusicDTO> musicInfosByPlaylist = musicService.findMusicInfosByPlaylist(byPlaylistId);

        if(musicInfosByPlaylist.size() < 10 || musicInfosByPlaylist.isEmpty()) {
            List<MusicDTO> musicDTOList = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                musicDTOList.add(musicService.findMusicInfo(musicRepository.findAll().get(i).getMusicId()));
            }
            return musicDTOList;
        }

        return musicInfosByPlaylist;
    }

    public List<MusicDTO> mood5List() {
        Long moodId;
        if(LocalDate.now().getMonthValue() == 12) {
            moodId = moodRepository.findMoodEntityByMood("캐롤").getMoodNum();
        }
        else
            moodId = moodRepository.findById((long) ((Math.random() * 10000) % 22)).get().getMoodNum();

        List<String> oneByMoodNum = musicMoodRepository.findOneByMoodNum(moodId);

        return musicService.musicEntitiesToMusicDTO(musicRepository.findByMusicId(oneByMoodNum));
    }

}

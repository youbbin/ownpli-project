package dbproject.ownpli.service;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.repository.MusicLikeRepository;
import dbproject.ownpli.repository.MusicRepository;
import dbproject.ownpli.repository.PlaylistMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final MusicService musicService;
    private final MusicLikeRepository musicLikeRepository;

    /**
     * playlist 많이 담은 순으로 음악 보내기
     * @return
     */
    public List<MusicDTO> findTop10Musics() {
        Optional<List<String>> distinctMusicIdOptional = playlistMusicRepository.findDistinctMusicId();
        List<MusicDTO> musicDTOList = new ArrayList<>();
        if(distinctMusicIdOptional.isEmpty() || distinctMusicIdOptional.get().size() < 10) {
            for (int i = 0; i < 10; i++) {
                musicDTOList.add(musicService.findMusicInfo(musicRepository.findAll().get(i).getMusicId()));
            }
            return musicDTOList;
        }

        List<String> distinctMusicId = distinctMusicIdOptional.get();
        List<MusicList> musicLists = new ArrayList<>();

        for(int i = 0; i < distinctMusicId.size(); i++) {
            musicLists.add(new MusicList(playlistMusicRepository.countByMusicId(distinctMusicId.get(i)), distinctMusicId.get(i)));
        }

        Collections.sort(musicLists, Collections.reverseOrder());


        for (int i = 0; i < 10; i++) {
            musicDTOList.add(musicService.findMusicInfo(musicLists.get(i).musicId));
        }

        return musicDTOList;
    }

    public List<MusicDTO> findTop10LikeList() {
        Optional<List<String>> musicIds = musicLikeRepository.findMusicIds();
        List<MusicDTO> musicDTOList = new ArrayList<>();
        if(musicIds.isEmpty() || musicIds.get().size() < 10) {
            for (int i = 0; i < 10; i++) {
                musicDTOList.add(musicService.findMusicInfo(musicRepository.findAll().get(i).getMusicId()));
            }
            return musicDTOList;
        }

        List<MusicEntity> byMusicId = musicRepository.findByMusicId(musicIds.get());
        return musicService.musicEntitiesToMusicDTO(byMusicId);
    }

    public class MusicList {
        private Long count;
        private String musicId;

        MusicList(Long count, String musicId) {
            this.count = count;
            this.musicId = musicId;
        }
    }
}

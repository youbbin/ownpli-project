package dbproject.ownpli.service;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import dbproject.ownpli.repository.MusicRepository;
import dbproject.ownpli.repository.PlaylistMusicRepository;
import dbproject.ownpli.repository.PlaylistRepository;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    /**
     * 유저이메일로 플레이리스트 목록 찾기
     * @param userId
     * @return
     */
    public List<PlaylistEntity> findPlaylistByUserId(String userId) {
        return (List<PlaylistEntity>) playlistRepository.findByUserId(userId);
    }

    /**
     * playlistID로 음악 리스트 찾기
     * @param playlistId
     * @return
     */
    public List<String> findMusicsByPlaylistId(String playlistId) {
        List<String> musicIds = playlistMusicRepository.findMusicIdsByPlaylistId(playlistId);
        //List<MusicEntity> musics = musicRepository.findByMusicId(musicIds);

        return musicIds;
    }

    /**
     * 새 플레이리스트 저장
     * @param userId
     * @param title
     * @param musicIds
     */
    public String savePlaylist(String userId, String title, List<String> musicIds) {
        String id = playlistRepository.findTop1ByUserIdOrderByPlaylistIdDesc(userId).getPlaylistId();
        StringTokenizer st = new StringTokenizer("p", id);
        Long idLong = Long.parseLong(st.nextToken());

        idLong++;

        id = "p" + idLong;

        playlistRepository.save(
            PlaylistEntity.builder()
                .playlistId(id)
                .playlistTitle(title)
                .userId(userRepository.findById(userId).get().getUserId())
                .build()
        );

        for(int i = 0; i < musicIds.size(); i++) {
            playlistMusicRepository.save(
                PlaylistMusicEntity.builder()
                    .playlistId(playlistRepository.findById(id).get().getPlaylistId())
                    .musicId(musicRepository.findById(musicIds.get(i)).get().getMusicId())
                    .build());
        }

        log.info("플레이리스트 저장");
        return id;
    }

    /**
     * 플레이리스트 삭제
     * @param playlistId
     */
    public void deletePlaylist(String playlistId) {
        playlistRepository.deleteById(playlistId);
        List<PlaylistMusicEntity> allByPlaylistId = playlistMusicRepository.findAllByPlaylistId(playlistId);

        for(int i = 0; i < allByPlaylistId.size(); i++)
            playlistMusicRepository.delete(allByPlaylistId.get(i));
    }

}

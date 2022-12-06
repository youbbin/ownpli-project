package dbproject.ownpli.service;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import dbproject.ownpli.dto.PlaylistDTO;
import dbproject.ownpli.repository.MusicRepository;
import dbproject.ownpli.repository.PlaylistMusicRepository;
import dbproject.ownpli.repository.PlaylistRepository;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<PlaylistDTO> findPlaylistByUserId(String userId) {
        List<PlaylistEntity> byUserId = playlistRepository.findByUserId(userId);
        List<PlaylistDTO> playlistDTOList = new ArrayList<>();
        for(int i = 0; i < byUserId.size(); i++) {
            playlistDTOList.add(
                PlaylistDTO.from(
                    byUserId.get(i),
                    playlistMusicRepository.findAllByPlaylistId(byUserId.get(i).getPlaylistId()).get(0).getDate()));
        }
        return playlistDTOList;

    }

    public PlaylistDTO getPlaylistDTOByPlaylistId(String playlistId) {
        return PlaylistDTO.from(
            playlistRepository.findById(playlistId).get(),
            playlistMusicRepository.findAllByPlaylistId(playlistId).get(0).getDate());
    }

    /**
     * playlistID로 음악 아이디들 찾기
     * @param playlistId
     * @return
     */
    public List<String> findMusicsByPlaylistId(String playlistId) {
        List<String> musicIds = playlistMusicRepository.findMusicIdsByPlaylistId(playlistId);
        //List<MusicEntity> musics = musicRepository.findByMusicId(musicIds);

        return musicIds;
    }

    public String findPlaylistIdByPlaylistTitleAndUserId(String title, String userId) {
        Optional<PlaylistEntity> byPlaylistTitleAndUserId = playlistRepository.findByPlaylistTitleAndUserId(title, userId);
        if(byPlaylistTitleAndUserId.isEmpty()) return null;
        else return byPlaylistTitleAndUserId.get().getPlaylistId();
    }

    /**
     * 새 플레이리스트 저장
     * @param userId
     * @param title
     * @param musicIds
     */
    public String savePlaylist(String userId, String title, List<String> musicIds) {

        Optional<PlaylistEntity> byPlaylistTitleAndUserId = playlistRepository.findByPlaylistTitleAndUserId(title, userId);
        if(!byPlaylistTitleAndUserId.isEmpty())
            return null;

        Optional<PlaylistEntity> idOptional = playlistRepository.findTop1ByUserIdOrderByPlaylistIdDesc(userId);
        String id = "";
        if(idOptional.isEmpty())
            id = "p1";
        else {
            id = idOptional.get().getPlaylistId();
            log.info("id={}", id);
            StringTokenizer st = new StringTokenizer(id, "p");
            Long idLong = Long.parseLong(st.nextToken());

            idLong++;

            id = "p" + idLong;
        }

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
                    .date(Date.valueOf(LocalDate.now()))
                    .build());
        }

        log.info("플레이리스트 저장");
        return id;
    }

    /**
     * 플레이리스트에 음악 추가
     * @Param userId
     * @param playlistId
     * @param musicIds
     * @return
     */
    public String addPlaylist(String userId, String playlistId, List<String> musicIds) {
        boolean flag = false;
        List<PlaylistEntity> byUserId = playlistRepository.findByUserId(userId);
        for(int i = 0; i < byUserId.size(); i++) {
            if(byUserId.get(i).getPlaylistId().equals(playlistId)) flag = true;
        }

        if(!flag) return null;

        for(int i = 0; i < musicIds.size(); i++) {
            playlistMusicRepository.save(
                PlaylistMusicEntity.builder()
                    .playlistId(playlistRepository.findById(playlistId).get().getPlaylistId())
                    .musicId(musicRepository.findById(musicIds.get(i)).get().getMusicId())
                    .date(Date.valueOf(LocalDate.now()))
                    .build());
        }

        log.info("플레이리스트 저장");
        return playlistId;
    }



    /**
     * 플레이리스트 삭제
     * @param playlistId
     */
    public void deletePlaylist(String playlistId) {
        List<PlaylistMusicEntity> allByPlaylistId = playlistMusicRepository.findAllByPlaylistId(playlistId);
        for(int i = 0; i < allByPlaylistId.size(); i++)
            playlistMusicRepository.delete(allByPlaylistId.get(i));

        playlistRepository.deleteById(playlistId);
    }

}

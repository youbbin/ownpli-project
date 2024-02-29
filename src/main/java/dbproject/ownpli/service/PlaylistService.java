package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import dbproject.ownpli.dto.PlaylistCreateRequest;
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

    public PlaylistDTO updatePlaylistTitle(String oldTitle, String newTitle, String userId) {
        Optional<PlaylistEntity> byPlaylistTitleAndUserId = playlistRepository.findByPlaylistTitleAndUserId(newTitle, userId);
        if (!byPlaylistTitleAndUserId.isEmpty())
            return null;

        int i = playlistRepository.updatePlaylistTitle(oldTitle, newTitle, userId);

        String playlistIdByPlaylistTitleAndUserId = findPlaylistIdByPlaylistTitleAndUserId(newTitle, userId);
        return getPlaylistDTOByPlaylistId(playlistIdByPlaylistTitleAndUserId);
    }

    /**
     * 유저이메일로 플레이리스트 목록 찾기
     *
     * @param userId
     * @return
     */
    public List<PlaylistDTO> findPlaylistByUserId(String userId) {
        List<PlaylistEntity> byUserId = playlistRepository.findByUserId(userId);
        if (byUserId.size() == 0) return null;

        List<PlaylistDTO> playlistDTOList = new ArrayList<>();

        for (int i = 0; i < byUserId.size(); i++) {
            playlistDTOList.add(PlaylistDTO.from(byUserId.get(i)));
        }
        return playlistDTOList;

    }

    public PlaylistDTO getPlaylistDTOByPlaylistId(String playlistId) {
        return PlaylistDTO.from(
                playlistRepository.findById(playlistId).get());
    }

    /**
     * playlistID로 음악 아이디들 찾기
     *
     * @param playlistId
     * @return
     */
    public List<String> findMusicsByPlaylistId(String playlistId) {
        List<String> musicIds = playlistMusicRepository.findMusicIdsByPlaylistId(playlistId);

        return musicIds;
    }

    public boolean playlistMusicDelete(String playlistId, List<String> musicIds) {
        List<Long> playlistMusicIdsByTitleAndMusicId = playlistMusicRepository.findPlaylistMusicIdsByTitleAndMusicId(playlistId, musicIds);
        playlistMusicRepository.deleteAllById(playlistMusicIdsByTitleAndMusicId);
        return true;
    }

    public String findPlaylistIdByPlaylistTitleAndUserId(String title, String userId) {
        Optional<PlaylistEntity> byPlaylistTitleAndUserId = playlistRepository.findByPlaylistTitleAndUserId(title, userId);
        if (byPlaylistTitleAndUserId.isEmpty()) return null;
        else return byPlaylistTitleAndUserId.get().getPlaylistId();
    }

    public List<String> findPlaylistIdsByPlaylistTitleAndUserId(String title, String userId) {
        List<String> list = List.of(title.split("@"));
        Optional<List<String>> byPlaylistTitleAndUserId = playlistRepository.findPlaylistIdsByPlaylistTitleAndUserId(list, userId);
        if (byPlaylistTitleAndUserId.isEmpty()) return null;
        else return byPlaylistTitleAndUserId.get();
    }

    public String savePlaylist(PlaylistCreateRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        if (playlistRepository.existsByPlaylistTitleAndUserEntity(request.getTitle(), user)) {
            return null;
        }

        Optional<PlaylistEntity> idOptional = playlistRepository.findFirstByOrderByPlaylistIdDesc();
        String id = "";
        if (idOptional.isEmpty())
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
                        .playlistTitle(request.getTitle())
                        .userEntity(user)
                        .build()
        );

        log.info("플레이리스트 생성");
        return id;
    }

    public String addSongsInPlaylist(String userId, String playlistId, List<String> musicIds) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        PlaylistEntity playlistEntity = playlistRepository.findByPlaylistIdAndUserEntity(playlistId, userEntity)
                .orElseThrow(() -> new NullPointerException("플레이리스트가 존재하지 않습니다."));

        musicRepository.findAllById(musicIds)
                .forEach(music -> playlistMusicRepository.save(PlaylistMusicEntity.of(playlistEntity, music)));

        log.info("플레이리스트 저장");
        return playlistId;
    }

    /**
     * 플레이리스트 삭제
     *
     * @param playlistId
     */
    public void deletePlaylist(List<String> playlistId) {
        List<PlaylistMusicEntity> allByPlaylistId = playlistMusicRepository.findAllByPlaylistId(playlistId);

        for (int i = 0; i < allByPlaylistId.size(); i++)
            playlistMusicRepository.delete(allByPlaylistId.get(i));

        playlistRepository.deleteAll(playlistRepository.findAllByPlaylistId(playlistId));
    }

}

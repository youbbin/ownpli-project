package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import dbproject.ownpli.dto.*;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final MusicLikeRepository musicLikeRepository;
    private final UserRepository userRepository;

    public PlaylistDTO updatePlaylistTitle(PlaylistUpdateRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        if (playlistRepository.existsByPlaylistTitleAndUserEntity(request.getOldTitle(), userEntity)) {
            throw new NullPointerException("존재하지 않는 제목입니다.");
        }

        PlaylistEntity playlistEntity = playlistRepository.findByPlaylistTitleAndUserEntity(request.getOldTitle(), userEntity)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 제목입니다."));
        playlistEntity.setPlaylistTitle(request.getNewTitle());
        playlistRepository.save(playlistEntity);

        return PlaylistDTO.from(playlistEntity);
    }

    public List<PlaylistDTO> findPlaylistByUserId(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        return playlistRepository.findByUserEntity(userEntity).stream()
                .map(PlaylistDTO::from)
                .collect(Collectors.toList());
    }

    public PlaylistMusicDTO findMusicsByPlaylistId(String playlistId) {
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        return PlaylistMusicDTO.from(PlaylistDTO.from(playlistEntity), collectMusicResponses(playlistEntity));
    }

    public PlaylistMusicDTO deletePlaylistMusics(String playlistId, PlaylistMusicRequest request) {
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        List<MusicEntity> musicEntities = musicRepository.findAllById(request.getMusicIds());
        playlistMusicRepository.deleteAllByPlaylistEntityAndMusicEntityIn(playlistEntity, musicEntities);

        return PlaylistMusicDTO.from(PlaylistDTO.from(playlistEntity), collectMusicResponses(playlistEntity));
    }

    private List<MusicResponse> collectMusicResponses(PlaylistEntity playlistEntity) {
        return playlistEntity.getPlaylistMusicEntities().stream()
                .map(playlistMusicEntity -> MusicResponse.ofPlaylistMusic(
                        playlistMusicEntity,
                        musicLikeRepository.countByMusicEntity(playlistMusicEntity.getMusicEntity())
                ))
                .collect(Collectors.toList());
    }

    public String findPlaylistIdByPlaylistTitleAndUserId(String title, String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        Optional<PlaylistEntity> byPlaylistTitleAndUserId = playlistRepository.findByPlaylistTitleAndUserEntity(title, userEntity);
        return byPlaylistTitleAndUserId.map(PlaylistEntity::getPlaylistId).orElse(null);
    }

    public List<String> findPlaylistIdsByPlaylistTitleAndUserId(String title, String userId) {
        List<String> list = List.of(title.split("@"));
        Optional<List<String>> byPlaylistTitleAndUserId = playlistRepository.findPlaylistIdsByPlaylistTitleAndUserId(list, userId);
        return byPlaylistTitleAndUserId.orElse(null);
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

            Long idLong = Long.parseLong(id.replace("p",""));
            id = "p" + ++idLong;
        }

        playlistRepository.save(PlaylistEntity.of(id, request.getTitle(), user));
        log.info("플레이리스트 생성");
        return id;
    }

    public void addSongsInPlaylist(String playlistId, List<Long> musicIds) {
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NullPointerException("플레이리스트가 존재하지 않습니다."));

        musicRepository.findAllById(musicIds)
                .forEach(music -> playlistMusicRepository.save(PlaylistMusicEntity.of(playlistEntity, music)));

        log.info("플레이리스트 저장");
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

package dbproject.ownpli.service;

import dbproject.ownpli.controller.dto.playlist.*;
import dbproject.ownpli.domain.User;
import dbproject.ownpli.domain.Music;
import dbproject.ownpli.domain.Playlist;
import dbproject.ownpli.domain.PlaylistMusic;
import dbproject.ownpli.controller.dto.music.MusicResponse;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final MusicLikeRepository musicLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlaylistDTO updatePlaylistTitle(PlaylistUpdateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        if (playlistRepository.existsByPlaylistTitleAndUser(request.getOldTitle(), user)) {
            throw new NullPointerException("존재하지 않는 제목입니다.");
        }

        Playlist playlist = playlistRepository.findByPlaylistTitleAndUser(request.getOldTitle(), user)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 제목입니다."));
        playlist.setPlaylistTitle(request.getNewTitle());
        playlistRepository.save(playlist);

        return PlaylistDTO.from(playlist);
    }

    public List<PlaylistDTO> findPlaylistByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        return playlistRepository.findByUser(user).stream()
                .map(PlaylistDTO::from)
                .collect(Collectors.toList());
    }

    public PlaylistMusicDTO findMusicsByPlaylistId(String playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        return PlaylistMusicDTO.from(PlaylistDTO.from(playlist), collectMusicResponses(playlist));
    }

    @Transactional
    public void deletePlaylistMusics(String playlistId, PlaylistMusicRequest request) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        List<Music> musicEntities = musicRepository.findAllById(request.getMusicIds());
        playlistMusicRepository.deleteAllByPlaylistAndMusicIn(playlist, musicEntities);
    }

    private List<MusicResponse> collectMusicResponses(Playlist playlist) {
        return playlist.getPlaylistMusicEntities().stream()
                .map(playlistMusicEntity -> MusicResponse.ofPlaylistMusic(
                        playlistMusicEntity,
                        musicLikeRepository.countByMusic(playlistMusicEntity.getMusic())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public String savePlaylist(PlaylistCreateRequest playlistCreateRequest) {
        User user = userRepository.findById(playlistCreateRequest.getUserId())
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        if (playlistRepository.existsByPlaylistTitleAndUser(playlistCreateRequest.getTitle(), user)) {
            return null;
        }

        Optional<Playlist> idOptional = playlistRepository.findFirstByOrderByPlaylistIdDesc();
        String id = "";
        if (idOptional.isEmpty())
            id = "p1";
        else {
            id = idOptional.get().getPlaylistId();
            log.info("id={}", id);

            Long idLong = Long.parseLong(id.replace("p", ""));
            id = "p" + ++idLong;
        }

        playlistRepository.save(Playlist.of(id, playlistCreateRequest.getTitle(), user));
        log.info("플레이리스트 생성");
        return id;
    }

    @Transactional
    public void addSongsInPlaylist(String playlistId, List<String> musicIds) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NullPointerException("플레이리스트가 존재하지 않습니다."));

        musicRepository.findAllById(musicIds)
                .forEach(music -> playlistMusicRepository.save(PlaylistMusic.of(playlist, music)));

        log.info("플레이리스트 저장");
    }

    @Transactional
    public void deletePlaylist(List<String> playlistId) {
        playlistMusicRepository.deleteAllByPlaylistIn(playlistRepository.findAllById(playlistId));
        playlistRepository.deleteAllById(playlistId);
    }

}

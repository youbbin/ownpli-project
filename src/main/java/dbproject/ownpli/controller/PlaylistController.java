package dbproject.ownpli.controller;

import dbproject.ownpli.dto.*;
import dbproject.ownpli.service.MusicService;
import dbproject.ownpli.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists(@PathVariable String userId) {
        return ResponseEntity.ok(playlistService.findPlaylistByUserId(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<PlaylistDTO> updateTitle(@RequestBody PlaylistUpdateRequest request) {
        return ResponseEntity.ok(playlistService.updatePlaylistTitle(request));
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistMusicDTO> findMusicList(@PathVariable String playlistId) {
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
    }

    @PostMapping("/{playlistId}/delete")
    public ResponseEntity<PlaylistMusicDTO> deleteMusics(
            @PathVariable String playlistId,
            @RequestBody PlaylistMusicRequest request
    ) {
        return ResponseEntity.ok(playlistService.deletePlaylistMusics(playlistId, request));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPlaylist(@RequestBody PlaylistCreateRequest request) {
        String playlistId = playlistService.savePlaylist(request);

        if(playlistId == null) {
            return new ResponseEntity<>("이미 존재하는 제목입니다.", HttpStatus.BAD_REQUEST);
        }

        playlistService.addSongsInPlaylist(playlistId, request.getSongIds());
        return ResponseEntity.ok("생성 성공");
    }

    @PostMapping("/{playlistId}/add")
    public ResponseEntity<PlaylistMusicDTO> addPlaylistMusic(
            @PathVariable String playlistId,
            @RequestBody PlaylistMusicRequest request
    ) {
        playlistService.addSongsInPlaylist(playlistId, request.getMusicIds());
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deletePlaylist(@RequestBody PlaylistDropRequest request) {
        playlistService.deletePlaylist(request.getPlaylistIds());
        return ResponseEntity.ok("삭제 완료");
    }

}

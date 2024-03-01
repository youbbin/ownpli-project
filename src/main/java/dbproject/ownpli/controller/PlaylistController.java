package dbproject.ownpli.controller;

import dbproject.ownpli.dto.*;
import dbproject.ownpli.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<PlaylistDTO> updateTitle(@RequestBody PlaylistUpdateRequest request) {
        return ResponseEntity.ok(playlistService.updatePlaylistTitle(request));
    }

    @GetMapping
    public ResponseEntity<PlaylistMusicDTO> findMusicList(@RequestParam(name = "id") String playlistId) {
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
    }

    @DeleteMapping("/{playlistId}/delete")
    public ResponseEntity<PlaylistMusicDTO> deleteMusics(
            @PathVariable String playlistId,
            @RequestBody PlaylistMusicRequest request
    ) {
        playlistService.deletePlaylistMusics(playlistId, request);
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
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

    @PutMapping("/{playlistId}/add")
    public ResponseEntity<PlaylistMusicDTO> addPlaylistMusic(
            @PathVariable String playlistId,
            @RequestBody PlaylistMusicRequest request
    ) {
        playlistService.addSongsInPlaylist(playlistId, request.getMusicIds());
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlaylist(@RequestBody PlaylistDropRequest request) {
        playlistService.deletePlaylist(request.getPlaylistIds());
        return ResponseEntity.ok("삭제 완료");
    }

}

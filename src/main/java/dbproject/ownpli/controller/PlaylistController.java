package dbproject.ownpli.controller;

import dbproject.ownpli.controller.dto.playlist.*;
import dbproject.ownpli.jwt.JwtProvider;
import dbproject.ownpli.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final JwtProvider jwtProvider;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists(@PathVariable String userId) {
        return ResponseEntity.ok(playlistService.findPlaylistByUserId(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<PlaylistDTO> updateTitle(HttpServletRequest servletRequest, @RequestBody PlaylistUpdateRequest request) {
        jwtProvider.isLogoutUser(servletRequest);
        return ResponseEntity.ok(playlistService.updatePlaylistTitle(request));
    }

    @GetMapping
    public ResponseEntity<PlaylistMusicDTO> findMusicList(HttpServletRequest request, @RequestParam(name = "id") String playlistId) {
        jwtProvider.isLogoutUser(request);
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
    }

    @DeleteMapping("/{playlistId}/delete")
    public ResponseEntity<PlaylistMusicDTO> deleteMusics(
            HttpServletRequest servletRequest,
            @PathVariable String playlistId,
            @RequestBody PlaylistMusicRequest request
    ) {
        jwtProvider.isLogoutUser(servletRequest);
        playlistService.deletePlaylistMusics(playlistId, request);
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPlaylist(HttpServletRequest request, @RequestBody PlaylistCreateRequest playlistCreateRequest) {
        jwtProvider.isLogoutUser(request);
        String playlistId = playlistService.savePlaylist(playlistCreateRequest);

        if(playlistId == null) {
            return new ResponseEntity<>("이미 존재하는 제목입니다.", HttpStatus.BAD_REQUEST);
        }

        playlistService.addSongsInPlaylist(playlistId, playlistCreateRequest.getSongIds());
        return ResponseEntity.ok("생성 성공");
    }

    @PutMapping("/{playlistId}/add")
    public ResponseEntity<PlaylistMusicDTO> addPlaylistMusic(
            HttpServletRequest servletRequest,
            @PathVariable String playlistId,
            @RequestBody PlaylistMusicRequest request
    ) {
        jwtProvider.isLogoutUser(servletRequest);
        playlistService.addSongsInPlaylist(playlistId, request.getMusicIds());
        return ResponseEntity.ok(playlistService.findMusicsByPlaylistId(playlistId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlaylist(
            HttpServletRequest servletRequest,
            @RequestBody PlaylistDropRequest request
    ) {
        jwtProvider.isLogoutUser(servletRequest);
        playlistService.deletePlaylist(request.getPlaylistIds());
        return ResponseEntity.ok("삭제 완료");
    }

}

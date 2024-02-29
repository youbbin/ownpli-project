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
    private final MusicService musicService;

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
            @RequestBody PlaylistMusicDeleteRequest request
    ) {
        return ResponseEntity.ok(playlistService.deletePlaylistMusics(playlistId, request));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPlaylist(@RequestBody PlaylistCreateRequest request) {
        String playlistId = playlistService.savePlaylist(request);

        if(playlistId == null) {
            return new ResponseEntity<>("이미 존재하는 제목입니다.", HttpStatus.BAD_REQUEST);
        }

        playlistService.addSongsInPlaylist(request.getUserId(), playlistId, request.getSongIds());

        return ResponseEntity.ok("생성 성공");
    }

    /**
     * 플레이리스트에 음악 추가
     * @param param
     * @return
     */
    @PostMapping("/addSongs")
    public ResponseEntity<String> updatePlaylist(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String playlistId = playlistService.findPlaylistIdByPlaylistTitleAndUserId(param.get("title").toString(), userId);
        if(playlistId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String musicTitle = param.get("songsTitle").toString();
        List<String> musicIds = musicService.findByTitle(List.of(musicTitle.split("@")));
//        String result = playlistService.addSongsInPlaylist(userId, playlistId, musicIds);

//        if(result == null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 플레이리스트 삭제
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deletePlaylist(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String title = param.get("title").toString();
        List<String> playlistIds = playlistService.findPlaylistIdsByPlaylistTitleAndUserId(title, userId);
        if(playlistIds == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        playlistService.deletePlaylist(playlistIds);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}

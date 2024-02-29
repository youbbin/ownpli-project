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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final MusicService musicService;

    @PostMapping("/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists(@PathVariable String userId) {
        return ResponseEntity.ok(playlistService.findPlaylistByUserId(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<PlaylistDTO> updateTitle(@RequestBody PlaylistUpdateRequest request) {
        return ResponseEntity.ok(playlistService.updatePlaylistTitle(request));
    }

    /**
     * playlistId로 playlist에 포함된 음악 list 정보 조회
     * @param param
     * @return
     */
    @PostMapping("/getlist/musics")
    public ResponseEntity<PlaylistMusicDTO> findMusicList(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String playlistTitle = param.get("playlistTitle").toString();
        String playlistId = playlistService.findPlaylistIdByPlaylistTitleAndUserId(playlistTitle, userId);
        if(playlistId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<String> musicsByPlaylistId = playlistService.findMusicsByPlaylistId(playlistId);
        List<MusicResponse> musicInfosByPlaylist = musicService.findMusicInfosByPlaylist(musicsByPlaylistId);
        return new ResponseEntity<>(
            PlaylistMusicDTO.from(playlistService.getPlaylistDTOByPlaylistId(playlistId), musicInfosByPlaylist),
            HttpStatus.OK);
    }

    @PostMapping("/getlist/delete")
    public ResponseEntity<PlaylistMusicDTO> deleteMusics(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String playlistTitle = param.get("playlistTitle").toString();
        String musicTitles = param.get("music").toString();

        List<String> byTitle = musicService.findByTitle(List.of(musicTitles.split("@")));

        String playlistId = playlistService.findPlaylistIdByPlaylistTitleAndUserId(playlistTitle, userId);
        if(playlistId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean b = playlistService.playlistMusicDelete(playlistId, byTitle);

        if(!b) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<String> musicsByPlaylistId = playlistService.findMusicsByPlaylistId(playlistId);
        List<MusicResponse> musicInfosByPlaylist = musicService.findMusicInfosByPlaylist(musicsByPlaylistId);

        return new ResponseEntity<>(
            PlaylistMusicDTO.from(playlistService.getPlaylistDTOByPlaylistId(playlistId), musicInfosByPlaylist), HttpStatus.OK);

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
        String result = playlistService.addSongsInPlaylist(userId, playlistId, musicIds);

        if(result == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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

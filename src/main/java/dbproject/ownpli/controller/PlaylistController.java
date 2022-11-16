package dbproject.ownpli.controller;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    /**
     * 회원의 모든 Playlist 조회
     * @param userId
     * @return
     */
    @GetMapping("/getlist")
    public ResponseEntity<List<PlaylistEntity>> findAllPlaylists(String userId) {
        List<PlaylistEntity> playlistEntities = playlistService.findPlaylistByUserId(userId);
        return new ResponseEntity<>(playlistEntities, HttpStatus.OK);
    }

    /**
     * playlistId로 음악 list 보내기
     * @param playlistId
     * @return
     */
    @GetMapping("/getlist")
    public ResponseEntity<MusicEntity[]> findMusicList(@RequestParam(name = "playlistId") String playlistId) {
        MusicEntity[] musicEntities = playlistService.findMusicsByPlaylistId(playlistId).toArray(new MusicEntity[0]);
        return new ResponseEntity<>(musicEntities, HttpStatus.OK);
    }

//    @GetMapping("/response-body-json-v1")
//    public ResponseEntity<HelloData> responseBodyJsonV1() {
//        HelloData helloData = new HelloData();
//        helloData.setUsername("userA");
//        helloData.setAge(20);
//
//        return new ResponseEntity<>(helloData, HttpStatus.OK);
}

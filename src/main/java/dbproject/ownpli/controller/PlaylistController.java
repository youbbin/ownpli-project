package dbproject.ownpli.controller;

import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.dto.PlaylistDTO;
import dbproject.ownpli.dto.PlaylistMusicDTO;
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

    //쿠키 없애기

    /**
     * 회원의 모든 Playlist 조회
     * @param param
     * @return
     * @address
     * /playlist/getlist
     */
    @PostMapping("/getlist")
    public ResponseEntity<List<PlaylistDTO>> findAllPlaylists(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        List<PlaylistDTO> playlistDTOList = playlistService.findPlaylistByUserId(userId);
        return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
    }

    /**
     * playlistId로 playlist에 포함된 음악 list 정보 조회
     * @param param
     * @return
     * @address
     * /playlist/getlist/title?q=~~~~
     * @container
     *  `@PathVariable` 어노테이션 뒤에 {} 안에 적은 변수 명을 name 속성의 값으로 넣는다.
     */
    @PostMapping("/getlist/title")
    public ResponseEntity<PlaylistMusicDTO> findMusicList(@RequestParam(name = "q") String playlistTitle, @RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String playlistId = playlistService.findPlaylistIdByPlaylistTitleAndUserId(playlistTitle, userId);
        if(playlistId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<String> musicsByPlaylistId = playlistService.findMusicsByPlaylistId(playlistId);
        List<MusicDTO> musicInfosByPlaylist = musicService.findMusicInfosByPlaylist(musicsByPlaylistId);
        return new ResponseEntity<>(
            PlaylistMusicDTO.from(playlistService.getPlaylistDTOByPlaylistId(playlistId), musicInfosByPlaylist),
            HttpStatus.OK);
    }

    /**
     * 플레이리스트 생성
     * @param param
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPlaylist(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String title = param.get("title").toString();
        Optional<String> songTitle = Optional.ofNullable(param.get("songsTitle").toString());
        String playlistId = playlistService.savePlaylist(userId, title);

        if(songTitle != null) {
            List<String> musicIds = musicService.findByTitle(musicService.divString(songTitle.get()));
            String result = playlistService.addPlaylist(userId, playlistId, musicIds);

            if(result == null )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity(HttpStatus.OK);
        }

        if(playlistId == null)
            return new ResponseEntity<>("이미 존재하는 제목입니다.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity("생성 성공", HttpStatus.OK);
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
        List<String> musicIds = musicService.findByTitle(musicService.divString(musicTitle));
        String result = playlistService.addPlaylist(userId, playlistId, musicIds);

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
        String playlistId = playlistService.findPlaylistIdByPlaylistTitleAndUserId(param.get("title").toString(), userId);
        if(playlistId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        playlistService.deletePlaylist(playlistId);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}

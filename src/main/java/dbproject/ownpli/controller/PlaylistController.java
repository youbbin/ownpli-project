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
        if(playlistDTOList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
    }

    /**
     * 플레이리스트 타이틀 바꾸기
     * @param param
     * @return
     */
    @PostMapping("/getlist/update")
    public ResponseEntity<PlaylistDTO> updateTitle(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String oldTitle = param.get("oldTitle").toString();
        String newTitle = param.get("newTitle").toString();

        PlaylistDTO playlistDTO = playlistService.updatePlaylistTitle(oldTitle, newTitle, userId);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
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
        List<MusicDTO> musicInfosByPlaylist = musicService.findMusicInfosByPlaylist(musicsByPlaylistId);
        return new ResponseEntity<>(
            PlaylistMusicDTO.from(playlistService.getPlaylistDTOByPlaylistId(playlistId), musicInfosByPlaylist),
            HttpStatus.OK);
    }

    @PostMapping("/getlist/delete")
    public ResponseEntity<PlaylistMusicDTO> deleteMusics(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        String playlistTitle = param.get("playlistTitle").toString();
        String musicTitles = param.get("music").toString();

        List<String> byTitle = musicService.findByTitle(musicService.divString(musicTitles));

        String playlistId = playlistService.findPlaylistIdByPlaylistTitleAndUserId(playlistTitle, userId);
        if(playlistId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean b = playlistService.playlistMusicDelete(playlistId, byTitle);

        if(!b) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<String> musicsByPlaylistId = playlistService.findMusicsByPlaylistId(playlistId);
        List<MusicDTO> musicInfosByPlaylist = musicService.findMusicInfosByPlaylist(musicsByPlaylistId);

        return new ResponseEntity<>(
            PlaylistMusicDTO.from(playlistService.getPlaylistDTOByPlaylistId(playlistId), musicInfosByPlaylist), HttpStatus.OK);

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
        Optional songTitle = Optional.ofNullable(param.get("songsTitle"));
        String playlistId = playlistService.savePlaylist(userId, title);

        if(songTitle.isPresent()) {
            List<String> musicIds = musicService.findByTitle(musicService.divString(songTitle.get().toString()));
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
        String title = param.get("title").toString();
        List<String> playlistIds = playlistService.findPlaylistIdsByPlaylistTitleAndUserId(title, userId);
        if(playlistIds == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        playlistService.deletePlaylist(playlistIds);

//        for(int i = 0; i < playlistIds.size(); i++) {
//            playlistService.deletePlaylist(playlistIds.get(i));
//        }

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}

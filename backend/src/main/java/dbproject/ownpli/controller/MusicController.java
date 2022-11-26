package dbproject.ownpli.controller;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.dto.SearchDTO;
import dbproject.ownpli.service.Mp3Service;
import dbproject.ownpli.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/musics")
public class MusicController {

    private final MusicService musicService;
    private final Mp3Service mp3Service;

    /**
     * 모든 음악 리스트 보내기
     * @return ResponseEntity [List [Model]]
     */
    @GetMapping("/getall")
    public ResponseEntity<List<MusicDTO>> getAllPlaylists() {
        List<MusicDTO> musicEntities = musicService.findAllMusics();
        return new ResponseEntity<>(musicEntities, HttpStatus.OK);
    }

    /**
     * 조건에 따라 음악 검색하기
     * @param model
     * @return
     */
    @GetMapping("/add")
    public ResponseEntity<List<MusicDTO>> getMusicAboutCondition(Model model) {
        List<String> genre = (List<String>) model.getAttribute("genre");
        List<String> moods = (List<String>) model.getAttribute("mood");
        List<MusicDTO> result;

        if(genre.isEmpty() && moods.isEmpty()) {
            result = musicService.findAllMusics();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        List<Long> byGenre = musicService.findGenresByGenre(genre);
        List<MusicEntity> musics = musicService.findMusicsByGenreIds(byGenre);

        List<Long> moodNumByMood = musicService.findMoodEntitiesByMood(moods);
        result = musicService.findMusicsByMoodIds(moodNumByMood, musics);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 제목과 가수 이름으로 음악을 검색하는 기능
     * @param musicSearch
     * @return
     * @Container
     * /musics/{musicSearch}
     */
    @GetMapping("/{musicSearch}")
    public ResponseEntity<SearchDTO> searchMusics(@PathVariable("musicSearch") String musicSearch) {

        List<MusicDTO> searchTitle = musicService.musicEntitiesToMusicDTO(musicService.findByTitleContain(musicSearch));
        List<MusicDTO> searchSinger = musicService.musicEntitiesToMusicDTO(musicService.findBySingerContain(musicSearch));

        return new ResponseEntity<>(SearchDTO.from(searchTitle, searchSinger), HttpStatus.OK);
    }

    /**
     * 단일 음악 정보 보내기
     * @param musicId
     * @return
     * @throws Exception
     */
    @GetMapping("/{musicId}")
    public ResponseEntity<MusicDTO> getMusics(@PathVariable("musicId") String musicId) throws JSONException {
        MusicDTO musicInfo = musicService.findMusicInfo(musicId);

        return new ResponseEntity<>(musicInfo, HttpStatus.OK);
    }

    /**
     * 가사 보내기
     * @param musicId
     * @return
     * @throws IOException
     */

    @GetMapping("/play/lyrics")
    public ResponseEntity<String> getLyrics(String musicId) throws IOException {
        return new ResponseEntity<>(musicService.readLirics(musicId), HttpStatus.OK);
    }

    /**
     * mp3파일을 보내기 위해 클라이언트와 통신
     * @param param
     * @return
     * @throws Exception
     * @container
     * JSon 포맷으로 전송된 request parameter 데이터를 받을 액션 메서드의 파라미터 변수에는 @RequestBody 어노테이션을 붙여주어야 한다.
     */
    @PostMapping("/play")
    public LinkedHashMap getAudio(@RequestBody LinkedHashMap param, String musicId) throws Exception{
        //위에 스트링으로 만들어준 객체를 답변을 위한 해쉬맵 객체에 넣어
        //프론트로 보내기 위해 적재
        return mp3Service.playAudio(param, musicId);
    }

}

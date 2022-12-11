package dbproject.ownpli.controller;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.dto.SearchDTO;
import dbproject.ownpli.service.Mp3Service;
import dbproject.ownpli.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;
    private final Mp3Service mp3Service;

//    @GetMapping("/getAllMusic")
//    public ResponseEntity<List<MusicDTO>> getAllMusics() {
//        return new ResponseEntity<>(musicService.musicEntitiesToMusicDTO(musicService.findAll()), HttpStatus.OK);
//    }

    /**
     * 조건에 따라 음악 검색하기
     * @param param
     * @return MusicDTO List
     */
    @PostMapping("/add")
    public ResponseEntity<List<MusicDTO>> getMusicAboutCondition(@RequestBody LinkedHashMap param) throws ParseException {
        return new ResponseEntity<>(musicService.addMusics(param), HttpStatus.OK);
    }

    /**
     * 가수 리스트 가져오기
     * @return
     */
    @PostMapping("/singer")
    public ResponseEntity<LinkedHashMap> getMusicAboutCondition() {
        LinkedHashMap<String, List> res = new LinkedHashMap<>();
//        String join = StringUtils.join(musicService.findSingerList(), '@');
        res.put("singerName", musicService.findSingerList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 제목과 가수 이름으로 음악을 검색하는 기능
     * @param param
     * @return
     * @Container
     * /music/search?q=!
     */
    @PostMapping("/search")
    public ResponseEntity<SearchDTO> searchMusics(@RequestBody LinkedHashMap param) {
        String musicSearch = param.get("musicSearch").toString();

        List<MusicDTO> searchTitle = musicService.musicEntitiesToMusicDTO(musicService.findByTitleContain(musicSearch));
        List<MusicDTO> searchSinger = musicService.musicEntitiesToMusicDTO(musicService.findBySingerContain(musicSearch));

        return new ResponseEntity<>(SearchDTO.from(searchTitle, searchSinger), HttpStatus.OK);
    }

    /**
     * 단일 음악 정보 보내기
     * @return
     * @url /music/title?q=~
     */
    @PostMapping("/title")
    public ResponseEntity<MusicDTO> getMusics(@RequestBody LinkedHashMap param) {
        String musicId = musicService.findOneMusicIdByTitle(param.get("title").toString()).getMusicId();
        MusicDTO musicInfo = musicService.findMusicInfo(musicId);

        return new ResponseEntity<>(musicInfo, HttpStatus.OK);
    }

    /**
     * 음악 정보에서 좋아요 눌렀을 때
     * @param param
     * @return
     */
    @PostMapping("/title/like")
    public ResponseEntity<MusicDTO> musicLikes(@RequestBody LinkedHashMap param) {
        String musicId = musicService.findOneMusicIdByTitle(param.get("title").toString()).getMusicId();
        String userId = musicService.musicLikeSetting(param.get("userId").toString(), musicId);

        if(userId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        MusicDTO musicInfo = musicService.findMusicInfo(musicId);

        return new ResponseEntity<>(musicInfo, HttpStatus.OK);
    }
//
//    @GetMapping("/get-image-dynamic-type")
//    @ResponseBody
//    public ResponseEntity<InputStreamResource> getImageDynamicType(@RequestParam("jpg") boolean jpg) {
//        MediaType contentType = jpg ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
//        InputStream in = jpg ?
//            getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg") :
//            getClass().getResourceAsStream("/com/baeldung/produceimage/image.png");
//        return ResponseEntity.ok()
//            .contentType(contentType)
//            .body(new InputStreamResource(in));
//    }

    /**
     * 가사 보내기
     * @param param
     * @return
     * @throws IOException
     * @url /play/lyrics?q=~
     */

    @PostMapping("/title/lyrics")
    public ResponseEntity<String> getLyrics(@RequestBody LinkedHashMap param) throws IOException {
        String musicId = musicService.findOneMusicIdByTitle(param.get("title").toString()).getMusicId();
        return new ResponseEntity<>(musicService.readLyrics(musicId), HttpStatus.OK);
    }

    /**
     * mp3파일을 보내기 위해 클라이언트와 통신
     * @return
     * @throws Exception
     * @url /music/play
     * @container
     * JSon 포맷으로 전송된 request parameter 데이터를 받을 액션 메서드의 파라미터 변수에는 @RequestBody 어노테이션을 붙여주어야 한다.
     */
    @PostMapping("/play")
    public ResponseEntity<LinkedHashMap> getAudio(@RequestBody LinkedHashMap param) throws Exception{
        MusicEntity musicEntity = musicService.findOneMusicIdByTitle(param.get("title").toString());
        LinkedHashMap linkedHashMap = mp3Service.playAudio(musicEntity.getMusicId());
        String userId = param.get("userId").toString();

        Optional likes = (Optional) param.get("likes");

        if(likes != null) {
            String s = musicService.musicLikeSetting(param.get("userId").toString(), musicEntity.getMusicId());

            if(!userId.equals(s)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        linkedHashMap.put("title", musicEntity.getTitle());
        linkedHashMap.put("singer", musicEntity.getSinger());
        linkedHashMap.put("likeStatus", !musicService.validateDuplicateLikes(userId, musicEntity.getMusicId()));

        return new ResponseEntity<>(linkedHashMap, HttpStatus.OK);
    }

}

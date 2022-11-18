package dbproject.ownpli.controller;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.service.Mp3Service;
import dbproject.ownpli.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musics")
public class MusicController {

    private final MusicService musicService;
    private final Mp3Service mp3Service;

    /**
     * 모든 음악 리스트 보내기
     * @param userId
     * @return
     */
    @GetMapping("/getAllList")
    public ResponseEntity<List<MusicEntity>> getAllPlaylists(String userId) {
        List<MusicEntity> musicEntities = musicService.findAllMusics();
        return new ResponseEntity<>(musicEntities, HttpStatus.OK);
    }

    /**
     * 제목과 가수 이름으로 음악을 검색하는 기능
     * @param musicSearch
     * @return
     *
     * /musics/{musicSearch}
     */
    @GetMapping("/{musicSearch}")
    public ResponseEntity<Model> searchMusics(@PathVariable("musicSearch") String musicSearch) {
        List<MusicEntity> searchMusictitle = musicService.findByTitleContain(musicSearch);
        List<MusicEntity> searchSinger = musicService.findBySingerContain(musicSearch);

        Model model = null;
        model.addAttribute(searchMusictitle);
        model.addAttribute(searchSinger);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * mp3파일을 보내기 위해 클라이언트와 통신
     * @param param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/cusvoice/audio")
    public LinkedHashMap getAudio(@RequestBody LinkedHashMap param) throws Exception{
        //위에 스트링으로 만들어준 객체를 답변을 위한 해쉬맵 객체에 넣어
        //프론트로 보내기 위해 적재
        return mp3Service.playAudio(param);
    }

}

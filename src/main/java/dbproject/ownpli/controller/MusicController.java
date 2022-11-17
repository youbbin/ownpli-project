package dbproject.ownpli.controller;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musics")
public class MusicController {

    private final MusicService musicService;

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


}

package dbproject.ownpli.controller;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.service.Mp3Service;
import dbproject.ownpli.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
    public ResponseEntity<List<MusicEntity>> getAllPlaylists(@RequestBody String userId) {
        List<MusicEntity> musicEntities = musicService.findAllMusics();
        return new ResponseEntity<>(musicEntities, HttpStatus.OK);
    }

    /**
     * 제목과 가수 이름으로 음악을 검색하는 기능
     * @param musicSearch
     * @return
     * @Container
     * /musics/{musicSearch}
     */
    @GetMapping("/{musicSearch}")
    public ResponseEntity<Model> searchMusics(@PathVariable("musicSearch") String musicSearch) {
        List<MusicEntity> searchTitle = musicService.findByTitleContain(musicSearch);
        List<MusicEntity> searchSinger = musicService.findBySingerContain(musicSearch);

        Model model = null;
        model.addAttribute(searchTitle);
        model.addAttribute(searchSinger);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * 단일 음악 정보 보내기
     * @param musicId
     * @return
     * @throws Exception
     */
    @GetMapping("/play")
    public Model getMusics(@RequestBody String musicId) throws IOException {
        MusicEntity byMusicId = musicService.findByMusicId(musicId);
        List<String> moodByMusicId = musicService.findMoodByMusicId(musicId);

        String inputFile = byMusicId.getImageFile();
        Path path = new File(inputFile).toPath();
        FileSystemResource resource = new FileSystemResource(path);

        Model model = null;
        model.addAttribute("musicId", byMusicId.getMusicId());
        model.addAttribute("title", byMusicId.getTitle());
        model.addAttribute("genre", byMusicId.getGenreId().getGenreName());
        model.addAttribute("mood", moodByMusicId);
        model.addAttribute("imageFile", resource);
        model.addAttribute("album", byMusicId.getAlbum());
        model.addAttribute("date", byMusicId.getDate());
        model.addAttribute("country", byMusicId.getCountry());
        model.addAttribute("lyrics", musicService.readLirics(musicId));

        return model;
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

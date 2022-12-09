package dbproject.ownpli.controller;

import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

//    로그인 전: 신곡, top10, 좋아요 순
//    로그인 후: 신곡, top10, 좋아요 순, 연령별 추천(플레이리스트 담은 순), 분위기1 별 노래5?(랜덤으로)

    /**
     * Top10 음악 찾기
     * @return
     * @container
     * 플레이리스트에 담은 곡이 10곡 이상이 안되면 첫 곡부터 10곡 출력
     */
    @GetMapping("/top10")
    public ResponseEntity<List<MusicDTO>> top10Musics() {
        return new ResponseEntity<>(homeService.findTop10Musics(), HttpStatus.OK);
    }

    /**
     * 좋아요 많이 받은 순으로 노래 보내기
     * @return
     */
    @GetMapping("/likes")
    public ResponseEntity<List<MusicDTO>> searchMusics() {
        return new ResponseEntity<>(homeService.findTop10LikeList(), HttpStatus.OK);
    }

    @PostMapping("/??")
    public ResponseEntity<List<MusicDTO>> ageMusics(@RequestBody LinkedHashMap param) {
        return new ResponseEntity<>(homeService.findTop10LikeList(), HttpStatus.OK);
    }

    @GetMapping("/moods")
    public ResponseEntity<List<MusicDTO>> moodMusics() {
        return new ResponseEntity<>(homeService.mood5List(), HttpStatus.OK);
    }
}

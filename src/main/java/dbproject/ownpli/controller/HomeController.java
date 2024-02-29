package dbproject.ownpli.controller;

import dbproject.ownpli.dto.HomeMusicListResponse;
import dbproject.ownpli.dto.MusicResponse;
import dbproject.ownpli.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

//    로그인 전: 신곡, top10, 좋아요 순
//    로그인 후: 신곡, top10, 좋아요 순, 연령별 추천(플레이리스트 담은 순), 분위기1 별 노래5?(랜덤으로)

    @PostMapping
    public ResponseEntity<LinkedHashMap<String, List<MusicResponse>>> homeController(@RequestBody(required = false) LinkedHashMap param) {
        LinkedHashMap<String, List<MusicResponse>> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("newSongs", homeService.findNewSongs());
        linkedHashMap.put("top10", homeService.findTop10Musics());
        linkedHashMap.put("mood", homeService.mood5List());

        Optional userId = Optional.ofNullable(param.get("userId"));

        if(userId.isPresent()) {
            linkedHashMap.put("age", homeService.ageList(userId.get().toString()));
        }

        return new ResponseEntity<>(linkedHashMap, HttpStatus.OK);
    }

    @GetMapping("/likes")
    public ResponseEntity<List<HomeMusicListResponse>> getTop10Likes() {
        return ResponseEntity.ok(homeService.findTop10LikeList());
    }

}

package dbproject.ownpli.controller;

import dbproject.ownpli.controller.dto.home.HomeMusicListResponse;
import dbproject.ownpli.jwt.JwtProvider;
import dbproject.ownpli.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;
    private final JwtProvider jwtProvider;

//    로그인 전: 신곡, top10, 좋아요 순
//    로그인 후: 신곡, top10, 좋아요 순, 연령별 추천(플레이리스트 담은 순), 분위기1 별 노래5?(랜덤으로)

    @GetMapping("/new")
    public ResponseEntity<List<HomeMusicListResponse>> getNewSong() {
        return ResponseEntity.ok(homeService.findNewSongs());
    }

    @GetMapping("/top")
    public ResponseEntity<List<HomeMusicListResponse>> findTop10() {
        return ResponseEntity.ok(homeService.findTop10Musics());
    }

    @GetMapping("/likes")
    public ResponseEntity<List<HomeMusicListResponse>> getTop10Likes() {
        return ResponseEntity.ok(homeService.findTop10LikeList());
    }

    @GetMapping("/mood")
    public ResponseEntity<List<HomeMusicListResponse>> getMoods() {
        return ResponseEntity.ok(homeService.mood5List());
    }

    @GetMapping("/age")
    public ResponseEntity<List<HomeMusicListResponse>> getAgeList(
            HttpServletRequest request,
            @RequestParam String userId
    ) {
        jwtProvider.isLogoutUserThenThrowException(request);
        return ResponseEntity.ok(homeService.getAgeList(userId));
    }

}

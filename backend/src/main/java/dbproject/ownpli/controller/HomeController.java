package dbproject.ownpli.controller;

import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    /**
     * Top10 음악 찾기
     * @return
     * @container
     * 플레이리스트에 담은 곡이 10곡 이상이 안되면 첫 곡부터 10곡 출력
     */
    @GetMapping
    public ResponseEntity<List<MusicDTO>> searchMusics() {
        return new ResponseEntity<>(homeService.findTop10Musics(), HttpStatus.OK);
    }
}

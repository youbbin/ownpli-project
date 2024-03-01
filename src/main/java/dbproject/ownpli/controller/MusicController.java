package dbproject.ownpli.controller;

import dbproject.ownpli.dto.*;
import dbproject.ownpli.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    @PostMapping("/add")
    public ResponseEntity<List<MusicSearchListResponse>> getMusicByCondition(@RequestBody MusicListRequest request) throws ParseException {
        return ResponseEntity.ok(musicService.searchByCondition(request));
    }

    @GetMapping("/singer")
    public ResponseEntity<SingerListResponse> getMusicAboutCondition() {
        return ResponseEntity.ok(musicService.findSingerList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MusicResponse>> searchMusics(@RequestParam String q) {
        return ResponseEntity.ok(musicService.searchSingerAndTitle(q));
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<MusicResponse> getMusicInfo(@PathVariable String musicId) {
        return ResponseEntity.ok(musicService.findMusicInfo(musicId));
    }

    @PostMapping("/{musicId}/like")
    public ResponseEntity<MusicResponse> musicLikes(@PathVariable String musicId, @RequestBody MusicLikeRequest request) {
        musicService.musicLikeSetting(request.getUserId(), musicId);
        return ResponseEntity.ok(musicService.findMusicInfo(musicId));
    }

    @GetMapping("/{musicId}/lyrics")
    public ResponseEntity<String> getLyrics(@PathVariable String musicId) throws IOException {
        return ResponseEntity.ok(musicService.readLyrics(musicId));
    }

}

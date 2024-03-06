package dbproject.ownpli.controller;

import dbproject.ownpli.controller.dto.base.PageDto;
import dbproject.ownpli.controller.dto.base.PageResponseData;
import dbproject.ownpli.controller.dto.music.*;
import dbproject.ownpli.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/music")
public class MusicController {

    private final MusicService musicService;

    @PostMapping
    public PageResponseData<List<MusicSearchListResponse>> getMusicByCondition(
            @RequestBody MusicListRequest request,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<MusicSearchListResponse> responses = musicService.searchByCondition(request, pageable);
        return PageResponseData.of(responses.toList(), PageDto.of(responses));
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

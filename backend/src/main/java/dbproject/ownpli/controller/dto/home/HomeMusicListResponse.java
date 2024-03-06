package dbproject.ownpli.controller.dto.home;

import dbproject.ownpli.domain.Music;
import dbproject.ownpli.domain.MusicLike;
import dbproject.ownpli.domain.MusicMood;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HomeMusicListResponse {

    private String musicId;
    private String title;
    private String imageFile;

    public static HomeMusicListResponse ofMusic(Music music) {
        return HomeMusicListResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .imageFile(music.getImageFile())
                .build();
    }

    public static HomeMusicListResponse ofMusicMood(MusicMood musicMood) {
        Music music = musicMood.getMusic();
        return HomeMusicListResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .imageFile(music.getImageFile())
                .build();
    }

    public static HomeMusicListResponse ofMusicLike(MusicLike musicLike) {
        Music music = musicLike.getMusic();
        return HomeMusicListResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .imageFile(music.getImageFile())
                .build();
    }
}

package dbproject.ownpli.dto;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicLikeEntity;
import dbproject.ownpli.domain.music.MusicMoodEntity;
import lombok.Builder;

public class HomeMusicListResponse {

    private Long musicId;
    private String title;
    private String imageFile;

    @Builder
    public HomeMusicListResponse(Long musicId, String title, String imageFile) {
        this.musicId = musicId;
        this.title = title;
        this.imageFile = imageFile;
    }

    public static HomeMusicListResponse ofMusic(MusicEntity musicEntity) {
        return HomeMusicListResponse.builder()
                .musicId(musicEntity.getMusicId())
                .title(musicEntity.getTitle())
                .imageFile(musicEntity.getImageFile())
                .build();
    }

    public static HomeMusicListResponse ofMusicMood(MusicMoodEntity musicMoodEntity) {
        MusicEntity musicEntity = musicMoodEntity.getMusicEntity();
        return HomeMusicListResponse.builder()
                .musicId(musicEntity.getMusicId())
                .title(musicEntity.getTitle())
                .imageFile(musicEntity.getImageFile())
                .build();
    }

    public static HomeMusicListResponse ofMusicLike(MusicLikeEntity musicLikeEntity) {
        MusicEntity musicEntity = musicLikeEntity.getMusicEntity();
        return HomeMusicListResponse.builder()
                .musicId(musicEntity.getMusicId())
                .title(musicEntity.getTitle())
                .imageFile(musicEntity.getImageFile())
                .build();
    }
}

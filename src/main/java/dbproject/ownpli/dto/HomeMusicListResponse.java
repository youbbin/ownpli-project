package dbproject.ownpli.dto;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicLikeEntity;
import dbproject.ownpli.domain.music.MusicMoodEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HomeMusicListResponse {

    private String musicId;
    private String title;
    private String imageFile;

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

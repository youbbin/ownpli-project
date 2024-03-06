package dbproject.ownpli.controller.dto.music;

import dbproject.ownpli.domain.Music;
import dbproject.ownpli.domain.MusicMood;
import dbproject.ownpli.domain.PlaylistMusic;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicResponse {

    private String musicId;
    private String title;
    private String singer;
    private String genre;
    private Long likes;
    private String imageFile;
    private String album;
    private String year;
    private String country;

    public static MusicResponse ofMusic(Music music, Long likes) {

        return MusicResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .singer(music.getSinger())
                .genre(music.getGenre().getGenre())
                .likes(likes)
                .album(music.getAlbum())
                .imageFile(music.getImageFile())
                .year(String.valueOf(music.getReleaseDate().getYear()))
                .country(music.getCountry())
                .build();
    }

    public static MusicResponse ofPlaylistMusic(PlaylistMusic playlistMusic, Long likes) {
        Music music = playlistMusic.getMusic();
        return MusicResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .singer(music.getSinger())
                .genre(music.getGenre().getGenre())
                .likes(likes)
                .album(music.getAlbum())
                .imageFile(music.getImageFile())
                .year(String.valueOf(music.getReleaseDate().getYear()))
                .country(music.getCountry())
                .build();
    }

    public static MusicResponse ofMusicMood(MusicMood musicMood, Long likes) {
        Music music = musicMood.getMusic();

        return MusicResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .singer(music.getSinger())
                .genre(music.getGenre().getGenre())
                .likes(likes)
                .album(music.getAlbum())
                .imageFile(music.getImageFile())
                .year(String.valueOf(music.getReleaseDate().getYear()))
                .country(music.getCountry())
                .build();
    }
}

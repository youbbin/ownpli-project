package dbproject.ownpli.dto;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicMoodEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
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

    public static MusicResponse ofMusic(MusicEntity musicEntity, Long likes) {

        return MusicResponse.builder()
                .musicId(musicEntity.getMusicId())
                .title(musicEntity.getTitle())
                .singer(musicEntity.getSinger())
                .genre(musicEntity.getGenreEntity().getGenre())
                .likes(likes)
                .album(musicEntity.getAlbum())
                .year(String.valueOf(musicEntity.getReleaseDate().getYear()))
                .country(musicEntity.getCountry())
                .build();
    }

    public static MusicResponse ofPlaylistMusic(PlaylistMusicEntity playlistMusicEntity, Long likes) {
        MusicEntity musicEntity = playlistMusicEntity.getMusicEntity();
        return MusicResponse.builder()
                .musicId(musicEntity.getMusicId())
                .title(musicEntity.getTitle())
                .singer(musicEntity.getSinger())
                .genre(musicEntity.getGenreEntity().getGenre())
                .likes(likes)
                .album(musicEntity.getAlbum())
                .year(String.valueOf(musicEntity.getReleaseDate().getYear()))
                .country(musicEntity.getCountry())
                .build();
    }

    public static MusicResponse ofMusicMood(MusicMoodEntity musicMoodEntity, Long likes) {
        MusicEntity musicEntity = musicMoodEntity.getMusicEntity();

        return MusicResponse.builder()
                .musicId(musicEntity.getMusicId())
                .title(musicEntity.getTitle())
                .singer(musicEntity.getSinger())
                .genre(musicEntity.getGenreEntity().getGenre())
                .likes(likes)
                .album(musicEntity.getAlbum())
                .year(String.valueOf(musicEntity.getReleaseDate().getYear()))
                .country(musicEntity.getCountry())
                .build();
    }
}

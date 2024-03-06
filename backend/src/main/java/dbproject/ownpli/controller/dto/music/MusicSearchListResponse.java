package dbproject.ownpli.controller.dto.music;

import dbproject.ownpli.domain.Music;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MusicSearchListResponse {

    private String musicId;
    private String title;
    private String singer;
    private String genre;
    private int year;
    private String country;

    public static MusicSearchListResponse of(Music music) {
        return MusicSearchListResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .singer(music.getSinger())
                .genre(music.getGenre().getGenre())
                .country(music.getCountry())
                .year(music.getReleaseDate().getYear())
                .build();
    }

}

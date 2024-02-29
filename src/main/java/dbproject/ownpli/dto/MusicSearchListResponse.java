package dbproject.ownpli.dto;

import dbproject.ownpli.domain.music.MusicEntity;
import lombok.Builder;

@Builder
public class MusicSearchListResponse {

    private String musicId;
    private String title;
    private String singer;
    private String genre;
    private int year;
    private String country;

    public static MusicSearchListResponse of(MusicEntity music) {
        return MusicSearchListResponse.builder()
                .musicId(music.getMusicId())
                .title(music.getTitle())
                .singer(music.getSinger())
                .genre(music.getGenreEntity().getGenre())
                .country(music.getCountry())
                .year(music.getDate().getYear())
                .build();
    }

}

package dbproject.ownpli.dto;

import dbproject.ownpli.domain.music.MusicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {

    private String musicId;
    private String title;
    private String singer;
    private String genre;
    private Long likes;
    private String imageFile;
    private String album;
    private String year;
    private String country;

    public static MusicDTO from(MusicEntity musicEntity, String genre, Long likes) {

        return new MusicDTO(
            musicEntity.getMusicId(),
            musicEntity.getTitle(),
            musicEntity.getSinger(),
            genre,
            likes,
            musicEntity.getImageFile().replaceFirst("D", "F").replaceAll("//", "/"),
            musicEntity.getAlbum(),
            musicEntity.getDate().toString().substring(0, 4),
            musicEntity.getCountry());
    }
}

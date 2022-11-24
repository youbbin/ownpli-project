package dbproject.ownpli.dto;

import dbproject.ownpli.domain.music.MusicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {

    private String musicId;
    private String title;
    private String singer;
    private String genre;
    private List<String> mood;
    private Long likes;
    private FileSystemResource imageFile;
    private String album;
    private Date date;
    private String country;

    public static MusicDTO from(MusicEntity musicEntity, String genre, List<String> moods, Long likes, FileSystemResource imageFile) {
        return new MusicDTO(
            musicEntity.getMusicId(),
            musicEntity.getTitle(),
            musicEntity.getSinger(),
            genre,
            moods,
            likes,
            imageFile,
            musicEntity.getAlbum(),
            musicEntity.getDate(),
            musicEntity.getCountry());
    }
}

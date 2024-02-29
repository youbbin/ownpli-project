package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "music")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicEntity {

    @Id
    @Column(name = "music id", nullable = false, length = 50)
    private Long musicId;

    //제목
    @Column(nullable = false, length = 200)
    private String title;

    //가수
    @Column(nullable = false, length = 200)
    private String singer;

    //앨범명
    @Column(length = 200)
    private String album;

    @OneToOne
    @JoinColumn(name = "genre num", referencedColumnName = "genre num")
    private GenreEntity genreEntity;

    //이미지파일경로
    @Column(name = "image file", nullable = false, length = 50)
    private String imageFile;

    @Column
    private Date date;

    //나라
    @Column(length = 50)
    private String country;

    //가사 파일 경로
    @Column(name = "lyrics file", length = 50)
    private String lyricsFile;

    //mp3 파일 경로
    @Column(name = "mp3 file", nullable = false, length = 50)
    private String mp3File;

    @OneToMany(mappedBy = "musicEntity")
    List<MusicMoodEntity> musicMoodEntities;

    @Builder
    public MusicEntity(Long musicId, String title, String singer, String album, GenreEntity genreEntity, String imageFile, Date date, String country, String lyricsFile, String mp3File) {
        this.musicId = musicId;
        this.title = title;
        this.singer = singer;
        this.album = album;
        this.genreEntity = genreEntity;
        this.imageFile = imageFile;
        this.date = date;
        this.country = country;
        this.lyricsFile = lyricsFile;
        this.mp3File = mp3File;
    }
}

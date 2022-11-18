package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "MUSIC")
@Table(name = "MUSIC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicEntity {

    @Id
    @Column(nullable = false, length = 50)
    private String musicId;

    //제목
    @Column(nullable = false, length = 50)
    private String title;

    //가수
    @Column(nullable = false, length = 50)
    private String singer;

    @OneToOne(fetch = FetchType.LAZY)
    private GenreEntity genreId;

    //이미지파일경로
    @Column(nullable = false, length = 50)
    private String imageFile;

    //앨범명
    @Column(nullable = false, length = 50)
    private String album;

    @Column(nullable = false)
    private Date date;

    //나라
    @Column(nullable = false, length = 50)
    private String country;

    //가사 파일 경로
    @Column(nullable = false, length = 50)
    private String liricsFile;

    //mp3 파일 경로
    @Column(nullable = false, length = 50)
    private String mp3File;





}

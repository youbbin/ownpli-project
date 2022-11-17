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

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 50)
    private String singer;

    @OneToOne(fetch = FetchType.LAZY)
    private GenreEntity genreId;

    @Column(nullable = false, length = 50)
    private String photo;

    @Column(nullable = false)
    private Date date;


}

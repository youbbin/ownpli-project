package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.music.MusicEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

/**
 * 조인으로 연동하기
 */
@Data
@Builder
@AllArgsConstructor
@Entity(name = "playlist_music")
@Table(name = "playlist-music")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistMusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long key;

    @ManyToOne
    @JoinColumn(name = "playlist id", referencedColumnName = "playlist id")
    private PlaylistEntity playlistEntity;

    @ManyToOne
    @JoinColumn(name = "music id", referencedColumnName = "music id")
    private MusicEntity musicEntity;

    @CreatedDate
    @Column(nullable = false)
    private Date date;

}

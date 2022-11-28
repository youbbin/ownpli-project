package dbproject.ownpli.domain.playlist;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 조인으로 연동하기
 */
@Data
@Builder
@AllArgsConstructor
@Entity(name = "playlist-music")
@Table(name = "playlist-music")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistMusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long key;

    @Column(name = "playlist id")
    private String playlistId;

    @Column(name = "music id")
    private String musicId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDate date;

}

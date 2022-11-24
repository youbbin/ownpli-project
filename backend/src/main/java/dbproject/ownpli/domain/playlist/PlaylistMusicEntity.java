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
@Entity
@Table(name = "PLAYLISTMUSIC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistMusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistMusicId;

    private String playlistId;

    private String musicId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDate date;

}

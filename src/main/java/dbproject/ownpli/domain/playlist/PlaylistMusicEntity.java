package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.music.MusicEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "PLAYLIST_MUSIC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistMusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistMusicId;

    @OneToOne(fetch = FetchType.LAZY)
    private PlaylistEntity playlistId;

    @ManyToOne(targetEntity = MusicEntity.class, fetch = FetchType.LAZY)        //다대일 단방향 매핑
    @JoinColumn(name = "MUSIC_ID")
    private MusicEntity musicId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDate date;

}

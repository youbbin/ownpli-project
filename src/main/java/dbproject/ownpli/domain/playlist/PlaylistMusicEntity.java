package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.music.MusicEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "playlist_music")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistMusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistMusicId;

    @ManyToOne
    @JoinColumn(name = "playlist_id", referencedColumnName = "playlist_id")
    private PlaylistEntity playlistEntity;

    @ManyToOne
    @JoinColumn(name = "music_id", referencedColumnName = "music_id")
    private MusicEntity musicEntity;

    @CreatedDate
    @Column(nullable = false)
    private LocalDate addDate;

    @Builder
    public PlaylistMusicEntity(PlaylistEntity playlistEntity, MusicEntity musicEntity) {
        this.playlistEntity = playlistEntity;
        this.musicEntity = musicEntity;
    }

    public static PlaylistMusicEntity of(PlaylistEntity playlistEntity, MusicEntity musicEntity) {
        return PlaylistMusicEntity.builder()
                .playlistEntity(playlistEntity)
                .musicEntity(musicEntity)
                .build();
    }
}

package dbproject.ownpli.domain;

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
public class PlaylistMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistMusicId;

    @ManyToOne
    @JoinColumn(name = "playlist_id", referencedColumnName = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "music_id", referencedColumnName = "music_id")
    private Music music;

    @CreatedDate
    @Column(nullable = false)
    private LocalDate addDate;

    @Builder
    public PlaylistMusic(Playlist playlist, Music music) {
        this.playlist = playlist;
        this.music = music;
    }

    public static PlaylistMusic of(Playlist playlist, Music music) {
        return PlaylistMusic.builder()
                .playlist(playlist)
                .music(music)
                .build();
    }
}

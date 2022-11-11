package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.music.MusicEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "PLAYLIST_MUSIC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistMusicEntity {

    @Id
    private Long playlistMusicId;

    @OneToOne(fetch = FetchType.LAZY)
    private PlaylistEntity playlistId;

    @ManyToOne(targetEntity = MusicEntity.class, fetch = FetchType.LAZY)        //다대일 단방향 매핑
    @JoinColumn(name = "MUSIC_ID")
    private MusicEntity musicId;

    @Column(nullable = false)
    private Date date;

}

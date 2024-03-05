package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "playlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist {

    /**
     * 플레이리스트 생성할 때 아이디 추가하기.
     * "pl" + xxxx 형식으로
     */
    @Id
    @Column(name = "playlist_id", nullable = false, length = 50)
    private String playlistId;

    @Setter
    @Column(nullable = false, length = 50)
    private String playlistTitle;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "playlist")
    private List<PlaylistMusic> playlistMusicEntities;

    @Builder
    public Playlist(String playlistId, String playlistTitle, User user) {
        this.playlistId = playlistId;
        this.playlistTitle = playlistTitle;
        this.user = user;
    }

    public static Playlist of(String playlistId, String playlistTitle, User user) {
        return Playlist.builder()
                .playlistId(playlistId)
                .playlistTitle(playlistTitle)
                .user(user)
                .build();
    }
}

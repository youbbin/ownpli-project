package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "playlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistEntity {

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
    private UserEntity userEntity;

    @OneToMany(mappedBy = "playlistEntity")
    private List<PlaylistMusicEntity> playlistMusicEntities;

    @Builder
    public PlaylistEntity(String playlistId, String playlistTitle, UserEntity userEntity) {
        this.playlistId = playlistId;
        this.playlistTitle = playlistTitle;
        this.userEntity = userEntity;
    }

    public static PlaylistEntity of(String playlistId, String playlistTitle, UserEntity userEntity) {
        return PlaylistEntity.builder()
                .playlistId(playlistId)
                .playlistTitle(playlistTitle)
                .userEntity(userEntity)
                .build();
    }
}

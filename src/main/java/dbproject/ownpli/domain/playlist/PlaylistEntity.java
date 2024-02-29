package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 조인으로 연동하기
 */
@Getter
@Entity(name = "playlist")
@Table(name = "playlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistEntity {

    /**
     * 플레이리스트 생성할 때 아이디 추가하기.
     * "pl" + xxxx 형식으로
     */
    @Id
    @Column(name = "playlist id", nullable = false, length = 50)
    private String playlistId;

    @Column(name = "playlist title", nullable = false, length = 50)
    private String playlistTitle;

    @ManyToOne
    @JoinColumn(name = "user id", referencedColumnName = "user id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "playlistEntity")
    private List<PlaylistMusicEntity> playlistMusicEntities;

    @Builder
    public PlaylistEntity(String playlistId, String playlistTitle, UserEntity userEntity) {
        this.playlistId = playlistId;
        this.playlistTitle = playlistTitle;
        this.userEntity = userEntity;
    }
}

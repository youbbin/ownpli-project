package dbproject.ownpli.domain.playlist;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 조인으로 연동하기
 */
@Data
@Builder
@AllArgsConstructor
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

    @Column(name = "user id", nullable = false)
    private String userId;
}

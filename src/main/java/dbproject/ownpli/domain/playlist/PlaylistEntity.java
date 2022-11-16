package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "PLAYLIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistEntity {

    /**
     * 플레이리스트 생성할 때 아이디 추가하기.
     * "pl" + xxxx 형식으로
     */
    @Id
    @Column(nullable = false, length = 50)
    private String playlistId;

    @Column(nullable = false, length = 50)
    private String playlistTitle;

    /**
     * OneToOne
     * optional
     * true = left outer join (default)
     * false = inner join
     */
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity userId;
}

package dbproject.ownpli.domain.playlist;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "playlist")
@Table(name = "PLAYLIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistEntity {

    @Id
    @Column(nullable = false, length = 50)
    private String playlistId;

    @Column(nullable = false, length = 50)
    private String playlistTitle;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity userId;
}

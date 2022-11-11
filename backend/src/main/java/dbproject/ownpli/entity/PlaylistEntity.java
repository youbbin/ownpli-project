package dbproject.ownpli.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "playlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistEntity {

    @Id
    private String playlistId;

    @Column(nullable = false, unique = true, length = 50)
    private String playlistTitle;

    @JoinColumn(name = "userId")
    private String userId;
}

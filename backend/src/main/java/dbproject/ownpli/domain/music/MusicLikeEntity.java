package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

/**
 * 조인으로 연동하기
 */

@Data
@Entity(name = "music_like")
@Builder
@Table(name = "music-like")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(name = "user id")
    private String userId;

    @Column(name = "music id")
    private String musicId;

}

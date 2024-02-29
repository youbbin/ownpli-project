package dbproject.ownpli.domain.music;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 조인으로 연동하기
 */

@Getter
@Entity(name = "music_like")
@Table(name = "music-like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long key;

    @ManyToOne
    @JoinColumn(name = "user ic", referencedColumnName = "user ic")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "music id", referencedColumnName = "music id")
    private MusicEntity musicEntity;

    @Builder
    public MusicLikeEntity(UserEntity userEntity, MusicEntity musicEntity) {
        this.userEntity = userEntity;
        this.musicEntity = musicEntity;
    }

    public static MusicLikeEntity of(UserEntity user, MusicEntity music) {
        return MusicLikeEntity.builder()
                .userEntity(user)
                .musicEntity(music)
                .build();
    }
}

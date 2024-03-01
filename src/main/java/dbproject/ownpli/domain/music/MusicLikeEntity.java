package dbproject.ownpli.domain.music;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "music_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicLikeId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "music_id", referencedColumnName = "music_id")
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

package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "music_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicLikeId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "music_id", referencedColumnName = "music_id")
    private Music music;

    @Builder
    public MusicLike(User user, Music music) {
        this.user = user;
        this.music = music;
    }

    public static MusicLike of(User user, Music music) {
        return MusicLike.builder()
                .user(user)
                .music(music)
                .build();
    }
}

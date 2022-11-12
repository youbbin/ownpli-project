package dbproject.ownpli.domain.music;

import dbproject.ownpli.domain.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "MUSICLIKE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicLikeEntity {

    @Id
    private Long musicLikeId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private List<UserEntity> userId;

    @OneToOne
    private MusicEntity musicId;

    //쓸데 불러오게 ~lazy?
}

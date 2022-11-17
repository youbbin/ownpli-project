package dbproject.ownpli.domain.music;

import dbproject.ownpli.domain.UserEntity;
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

    /**
     * 관계의 소유를 가지지 않은 곳에서 @JoinColumn 어노테이션을 사용하면 안됨
     */
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<UserEntity> userId;

    @OneToOne
    private MusicEntity musicId;

    //쓸데 불러오게 ~lazy?
}

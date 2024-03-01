package dbproject.ownpli.domain;

import dbproject.ownpli.domain.music.MusicLikeEntity;
import dbproject.ownpli.dto.UserJoinRequest;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate //update 할때 실제 값이 변경됨 컬럼으로만 update 쿼리를 만듦
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;      //email?

    @Column(nullable = false, length = 50)
    private String password;

    @Setter
    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToMany(mappedBy = "userEntity")
    private List<MusicLikeEntity> musicLikeEntities;

    @Builder
    public UserEntity(String userId, String password, String name, int age, Sex sex) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public static UserEntity of(UserJoinRequest request) {
        return UserEntity.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .sex(request.getSex())
                .age(request.getAge())
                .build();
    }
}

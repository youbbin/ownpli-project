package dbproject.ownpli.domain;

import dbproject.ownpli.controller.dto.user.UserJoinRequest;
import dbproject.ownpli.domain.value.Role;
import dbproject.ownpli.domain.value.Sex;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<MusicLike> musicLikeEntities;

    @Builder
    public User(String userId, String password, String name, Role role, int age, Sex sex) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.role = role;
    }

    public static User of(UserJoinRequest request) {
        return User.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .sex(request.getSex())
                .age(request.getAge())
                .role(Role.USER)
                .build();
    }
}

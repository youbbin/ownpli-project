package dbproject.ownpli.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserEntity {
    @Id
    private String userID;

    @Column(nullable = false, length = 50)
    private String passward;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private int sex;

}

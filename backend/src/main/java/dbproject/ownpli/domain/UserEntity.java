package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserEntity {
    @Id
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;      //email?

    @Column(nullable = false, length = 50)
    private String passward;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private int sex;

}

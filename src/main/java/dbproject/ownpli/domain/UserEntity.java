package dbproject.ownpli.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate //update 할때 실제 값이 변경됨 컬럼으로만 update 쿼리를 만듦
public class UserEntity {

    @Id
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;      //email?

    @Column(nullable = false, length = 50)
    private String passward;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private int sex;

    public UserEntity update(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
        return this;
    }

}

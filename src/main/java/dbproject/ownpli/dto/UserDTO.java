package dbproject.ownpli.dto;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private int age;
    private int sex;

    public static UserDTO from(UserEntity userEntity) {
        return new UserDTO(userEntity.getUserId(), userEntity.getName(), userEntity.getAge(), userEntity.getSex());
    }
}

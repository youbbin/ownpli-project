package dbproject.ownpli.dto;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private String userId;
    private String name;
    private int age;
    private int sex;

    public static UserInfoResponse from(UserEntity userEntity) {
        return new UserInfoResponse(userEntity.getUserId(), userEntity.getName(), userEntity.getAge(), userEntity.getSex());
    }
}

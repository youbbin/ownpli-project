package dbproject.ownpli.dto;

import dbproject.ownpli.domain.UserEntity;
import lombok.*;

@Getter
@Builder
public class UserInfoResponse {

    private String userId;
    private String name;
    private int age;
    private String sex;

    public static UserInfoResponse from(UserEntity userEntity) {
        return UserInfoResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .age(userEntity.getAge())
                .sex(userEntity.getSex().getSexName())
                .build();
    }
}

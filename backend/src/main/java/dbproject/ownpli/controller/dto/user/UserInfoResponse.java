package dbproject.ownpli.controller.dto.user;

import dbproject.ownpli.domain.User;
import lombok.*;

@Getter
@Builder
public class UserInfoResponse {

    private String userId;
    private String name;
    private int age;
    private String sex;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .age(user.getAge())
                .sex(user.getSex().getSexName())
                .build();
    }
}

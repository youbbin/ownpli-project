package dbproject.ownpli.controller.dto.user;

import dbproject.ownpli.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private String userId;
    private String userName;

    public static UserResponse of(UserEntity user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .build();
    }

}

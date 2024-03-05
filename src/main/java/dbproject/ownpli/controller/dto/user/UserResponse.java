package dbproject.ownpli.controller.dto.user;

import dbproject.ownpli.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private String userId;
    private String userName;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .build();
    }

}

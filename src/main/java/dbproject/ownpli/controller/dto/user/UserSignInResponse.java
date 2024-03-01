package dbproject.ownpli.controller.dto.user;

import dbproject.ownpli.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSignInResponse {

    private String userId;
    private String userName;

    public static UserSignInResponse of(UserEntity user) {
        return UserSignInResponse.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .build();
    }

}

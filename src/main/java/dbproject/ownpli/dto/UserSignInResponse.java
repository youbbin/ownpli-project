package dbproject.ownpli.dto;

import dbproject.ownpli.domain.UserEntity;
import lombok.Builder;

@Builder
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

package dbproject.ownpli.jwt;

import dbproject.ownpli.controller.dto.user.UserResponse;
import dbproject.ownpli.domain.value.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Subject {

    private final String userId;
    private final String name;
    private final Type type;

    public static Subject atk(UserResponse userResponse) {
        return Subject.builder()
                .userId(userResponse.getUserId())
                .name(userResponse.getUserName())
                .type(Type.ATK)
                .build();
    }

    public static Subject rtk(UserResponse userResponse) {
        return Subject.builder()
                .userId(userResponse.getUserId())
                .name(userResponse.getUserName())
                .type(Type.RTK)
                .build();
    }
}

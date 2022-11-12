package dbproject.ownpli.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
    private String userId;
    private String name;
    private String provider;

    public UserEntity toUser() {
        return UserEntity.builder()
            .userId(userId)
            .name(name)
            .provider(provider)
            .build();
    }
}

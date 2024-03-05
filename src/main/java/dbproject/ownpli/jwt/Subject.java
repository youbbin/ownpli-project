package dbproject.ownpli.jwt;

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

    public static Subject atk(String userId, String name) {
        return Subject.builder()
                .userId(userId)
                .name(name)
                .type(Type.ATK)
                .build();
    }

    public static Subject rtk(String userId, String name) {
        return Subject.builder()
                .userId(userId)
                .name(name)
                .type(Type.RTK)
                .build();
    }
}

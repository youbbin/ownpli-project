package dbproject.ownpli.controller.dto.user;

import dbproject.ownpli.domain.value.Sex;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    private String userId;
    private String password;
    private String name;
    private int age;
    private Sex sex;

}

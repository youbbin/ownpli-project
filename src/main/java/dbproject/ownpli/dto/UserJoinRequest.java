package dbproject.ownpli.dto;

import dbproject.ownpli.domain.Sex;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    private String userId;
    private String password;
    private String name;
    private int age;
    private Sex sex;

}

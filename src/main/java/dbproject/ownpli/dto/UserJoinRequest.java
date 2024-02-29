package dbproject.ownpli.dto;

import lombok.Getter;

@Getter
public class UserJoinRequest {

    private String userId;
    private String password;
    private String name;
    private int age;
    private int sex;

}

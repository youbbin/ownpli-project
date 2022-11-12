package dbproject.ownpli.controller;

import dbproject.ownpli.domain.user.UserEntity;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /*
    * 멤버조회
    * */

    @GetMapping("user")
    public List<UserEntity> findAllMember() {
        return userService.findUsers();
    }

}

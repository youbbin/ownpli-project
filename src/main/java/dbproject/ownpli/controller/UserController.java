package dbproject.ownpli.controller;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 멤버조회
     * @return
     */
    @GetMapping("user")
    public List<UserEntity> findAllMember() {
        return userService.findUsers();
    }

    /**
     * 회원가입
     * @param userEntity
     */
    @PostMapping("/signup")
    public void signUpUser(@RequestParam UserEntity userEntity) {
        userService.join(userEntity);
    }





}

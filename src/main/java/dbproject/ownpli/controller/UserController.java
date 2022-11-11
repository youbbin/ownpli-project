package dbproject.ownpli.controller;

import dbproject.ownpli.entity.UserEntity;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    /*
    * 멤버조회
    * */

    @GetMapping("user")
    public List<UserEntity> findAllMember() {
        return userRepository.findAll();
    }

    /*
    * 회원가입
    * */

    @PostMapping("user")
    public UserEntity signUp() {
        final UserEntity user = UserEntity.builder()
            .userID("testuser@naver.com")
            .name("test user")
            .passward("1234")
            .age(14)
            .sex(1)
            .build();
        return userRepository.save(user);
    }
}

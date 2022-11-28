package dbproject.ownpli.controller;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

//    /**
//     * 멤버조회
//     * @return ResponseEntity [List [UserEntity]]]
//     */
//    @GetMapping("user")
//    public ResponseEntity<List<UserEntity>> findAllMember() {
//        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
//    }

    /**
     * 회원가입
     * @param userId
     * @param password
     * @param name
     * @param age
     * @param sex
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestParam String userId,
                                             @RequestParam String password,
                                             @RequestParam String name,
                                             @RequestParam int age,
                                             @RequestParam int sex) {


        String getUserId = userService.join(new UserEntity(userId, password, name, age, sex));

        if(!userId.equals(getUserId))
            return new ResponseEntity<>("회원가입 실패", HttpStatus.BAD_REQUEST);

        return new ResponseEntity("회원가입 성공", HttpStatus.OK);
    }

    /**
     * 로그인
     * @param loginId
     * @param password
     * @return ResponseEntity [Coolie]
     */
    @PostMapping("/login")
    public ResponseEntity<Cookie> login(@RequestParam String loginId, @RequestParam String password) {
        UserEntity loginUser = userService.login(loginId, password);

        //로그인 실패
        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //로그인 성공 처리

        //쿠키에 시간 정보를 주지 않으면 세션 쿠기(브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("userId", loginUser.getUserId());
        return new ResponseEntity<>(idCookie, HttpStatus.OK);

    }

    /**
     * {
     *     "userId": "test123@naver.com",
     *     "password": "1234",
     *     "name": "test_user",
     *     "age": 25,
     *     "sex": 1
     * }
     */




}

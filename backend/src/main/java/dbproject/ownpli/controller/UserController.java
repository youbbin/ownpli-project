package dbproject.ownpli.controller;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.dto.UserDTO;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param user
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody UserEntity user) {
        /**
         * {
         *     "userId": "test123@naver.com",
         *     "password": "1234",
         *     "name": "test_user",
         *     "age": 25,
         *     "sex": 1
         * }
         */

        String getUserId = userService.join(user);

        if(getUserId == null)
            return new ResponseEntity<>("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity("회원가입 성공", HttpStatus.OK);
    }

    /**
     * 로그인
     * @param param
     * @return ResponseEntity [Coolie]
     */
    @PostMapping("/login")
    public ResponseEntity<LinkedHashMap> login(@RequestBody LinkedHashMap<String, String> param) {
        String loginId = param.get("userId");
        String password = param.get("password");

        UserEntity loginUser = userService.login(loginId, password);

        //로그인 실패
        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("userId", loginId);

        return new ResponseEntity<>(linkedHashMap, HttpStatus.OK);

    }

    /**
     * 유저 정보 가져오기
     * @param param
     * @return
     */
    @GetMapping("/mypage")
    public ResponseEntity<UserDTO> homeLogin(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity loginUser = userService.findByUserId(userId);

        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UserDTO.from(loginUser), HttpStatus.OK);
    }

    /**
     * 유저 닉네임 변경
     * @param param
     * @return
     */
    @PostMapping("/mypage/update")
    public ResponseEntity<UserDTO> changeName(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String name = param.get("name").toString();

        UserDTO userDTO = userService.updateNicknameByUserId(name, userId);
        if(userDTO == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }
//
//    /**
//     * 로그아웃
//     * @param response
//     * @return
//     */
//    @PostMapping("/logout")
//    public ResponseEntity<HttpServletResponse> logout(HttpServletResponse response) {
//        expiredCookie(response, "userId");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    private void expiredCookie(HttpServletResponse response, String cookieName) {
//        Cookie cookie = new Cookie(cookieName, null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }

}

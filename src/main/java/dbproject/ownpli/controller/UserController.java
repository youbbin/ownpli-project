package dbproject.ownpli.controller;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.dto.*;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody UserJoinRequest request) {
        userService.join(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<UserSignInResponse> login(@RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String userId) {
        return ResponseEntity.ok(userService.findByUserId(userId));
    }

    @PostMapping("/mypage/update")
    public ResponseEntity<UserInfoResponse> changeName(@RequestBody UserNameUpdateRequest request) {
        return ResponseEntity.ok(userService.updateNicknameByUserId(request));
    }

}

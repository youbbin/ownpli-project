package dbproject.ownpli.controller;

import dbproject.ownpli.controller.dto.user.*;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signUpUser(@RequestBody UserJoinRequest request) {
        return ResponseEntity.ok(userService.join(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String userId) {
        return ResponseEntity.ok(userService.findByUserId(userId));
    }

    @PutMapping("/mypage/update")
    public ResponseEntity<UserInfoResponse> changeName(@RequestBody UserNameUpdateRequest request) {
        return ResponseEntity.ok(userService.updateNicknameByUserId(request));
    }

}

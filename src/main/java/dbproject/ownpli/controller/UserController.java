package dbproject.ownpli.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dbproject.ownpli.controller.dto.token.TokenResponse;
import dbproject.ownpli.controller.dto.user.*;
import dbproject.ownpli.jwt.JwtProvider;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signUpUser(@RequestBody UserJoinRequest request) {
        return ResponseEntity.ok(userService.join(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserSignInRequest request) throws JsonProcessingException {
        UserResponse userResponse = userService.login(request);
        return ResponseEntity.ok(jwtProvider.createTokensByLogin(userResponse));
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

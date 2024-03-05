package dbproject.ownpli.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dbproject.ownpli.controller.dto.token.TokenResponse;
import dbproject.ownpli.controller.dto.user.*;
import dbproject.ownpli.domain.UserDetails;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signUpUser(@RequestBody UserJoinRequest request) {
        return ResponseEntity.ok(userService.join(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserSignInRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String userId) {
        return ResponseEntity.ok(userService.findByUserId(userId));
    }

    @PutMapping("/mypage/update")
    public ResponseEntity<UserInfoResponse> changeName(
            HttpServletRequest request,
            @RequestBody UserNameUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(userService.updateNicknameByUserId(request, updateRequest));
    }

    @GetMapping("/user/reissue")
    public ResponseEntity<TokenResponse> reissue(@AuthenticationPrincipal UserDetails userDetails) throws JsonProcessingException {
        return ResponseEntity.ok(userService.reissue(userDetails));
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request, @RequestParam String userId) {
        userService.logout(request, userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

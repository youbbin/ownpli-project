package dbproject.ownpli.service;

import dbproject.ownpli.controller.dto.user.*;
import dbproject.ownpli.domain.User;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserInfoResponse join(UserJoinRequest request) {

        if (userRepository.existsById(request.getUserId())) {
            throw new NullPointerException("이미 존재하는 아이디입니다.");
        }

        log.info("length={}", passwordEncoder.encode(request.getPassword()).length());
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = userRepository.save(User.of(request));
        return UserInfoResponse.from(user);
    }


    public UserResponse login(UserSignInRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );
        if (!matches) throw new NullPointerException("아이디 혹은 비밀번호를 확인하세요.");

        return UserResponse.of(user);
    }

    public UserInfoResponse findByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 아이디입니다."));

        return UserInfoResponse.from(user);
    }

    @Transactional
    public UserInfoResponse updateNicknameByUserId(UserNameUpdateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException("존재하지 않는 아이디입니다."));

        user.setName(request.getName());
        userRepository.save(user);
        return UserInfoResponse.from(user);
    }

}

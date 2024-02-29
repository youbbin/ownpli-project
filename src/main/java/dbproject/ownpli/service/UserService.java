package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.dto.*;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(UserJoinRequest request) {

        if (userRepository.existsById(request.getUserId())) {
            throw new NullPointerException("이미 존재하는 아이디입니다.");
        }

        userRepository.save(UserEntity.of(request));
        log.info("회원가입 완료");
    }


    public UserSignInResponse login(UserSignInRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        if (!userEntity.getPassword().equals(request.getPassword())) {
            throw new NullPointerException("잘못된 비밀번호 입니다.");
        }

        return UserSignInResponse.of(userEntity);
    }

    public UserInfoResponse findByUserId(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 아이디입니다."));

        return UserInfoResponse.from(userEntity);
    }

    @Transactional
    public UserInfoResponse updateNicknameByUserId(UserNameUpdateRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException("존재하지 않는 아이디입니다."));

        userEntity.setName(request.getName());
        userRepository.save(userEntity);
        return UserInfoResponse.from(userEntity);
    }

}

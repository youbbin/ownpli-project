package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.dto.UserDTO;
import dbproject.ownpli.dto.UserJoinRequest;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void join(UserJoinRequest request) {

        if (userRepository.existsById(request.getUserId())) {
            throw new NullPointerException("이미 존재하는 아이디입니다.");
        }

        userRepository.save(UserEntity.of(request));
        log.info("회원가입 완료");
    }

    /**
     * 로그인
     *
     * @param loginId
     * @param password
     * @return
     */
    public UserEntity login(String loginId, String password) {
        return userRepository.findById(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    /**
     * 회원 단일 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserEntity findByUserId(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) return null;

        return user.get();
    }


    /**
     * 회원 닉네임 수정
     *
     * @param name
     * @param userId
     * @return
     */
    public UserDTO updateNicknameByUserId(String name, String userId) {
        int i = userRepository.updateUserName(name, userId);

        UserEntity byUserId = findByUserId(userId);
        if (byUserId == null) return null;

        return UserDTO.from(byUserId);
    }

}

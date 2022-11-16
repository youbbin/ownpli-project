package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     * @param userEntity
     * @return
     */
    public String join(UserEntity userEntity) {
        validateDuplicateUser(userEntity);
        userRepository.save(userEntity);
        return userEntity.getUserId();
    }

    /**
     * 회원 아이디 중복검사
     * @param user
     */
    private void validateDuplicateUser(UserEntity user) {
        Optional<UserEntity> findUser = userRepository.findById(user.getUserId());
        UserEntity user2 = findUser.get();
        if(!user2.equals(null))
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    /**
     * 회원 전체 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserEntity> findUsers() {
        return userRepository.findAll();
    }

    /**
     * 회원 단일 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserEntity findByUserId(String userId) {
        return userRepository.findById(userId).get();
    }


    /**
     * 회원 닉네임 수정
     * @param nickname
     * @param userId
     * @return
     */
    public void updateNicknameByUserId(String nickname, String userId) {
        int i = userRepository.updateUserNickname(nickname, userId);
    }


}

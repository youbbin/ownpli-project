package dbproject.ownpli.service;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.dto.UserDTO;
import dbproject.ownpli.repository.PlaylistMusicRepository;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PlaylistMusicRepository playlistMusicRepository;

    /**
     * 회원가입
     * @param userEntity
     * @return
     */
    public String join(UserEntity userEntity) {
        boolean flag = validateDuplicateUser(userEntity);
        if(!flag) return null;

        userRepository.save(userEntity);
        log.info("회원가입 완료");
        return userEntity.getUserId();
    }

    /**
     * 회원 아이디 중복검사
     * @param user
     */
    private boolean validateDuplicateUser(UserEntity user) {
        Optional<UserEntity> findUser = userRepository.findById(user.getUserId());
        if(!findUser.isEmpty())
            return false;
        return true;
    }

    /**
     * 로그인
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
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()) return null;

        return user.get();
    }


    /**
     * 회원 닉네임 수정
     * @param name
     * @param userId
     * @return
     */
    public UserDTO updateNicknameByUserId(String name, String userId) {
        int i = userRepository.updateUserName(name, userId);

        UserEntity byUserId = findByUserId(userId);
        if(byUserId == null) return null;

        return UserDTO.from(byUserId);
    }

}

package dbproject.ownpli.repository;

import dbproject.ownpli.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * findByEmailAndProvider 메서드를 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단합니다.
     * @param userId
     * @param provider
     * @return
     */
    Optional<UserEntity> findByUserIdAndProvider(String userId, String provider);

//    /**
//     * 단일 유저 조회
//     *
//     * @param id
//     * @return
//     */
//
//    @Query("SELECT u FROM UserEntity u WHERE u.userID = :id")
//    UserEntity findById(String id);

}

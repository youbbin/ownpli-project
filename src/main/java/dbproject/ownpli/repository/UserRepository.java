package dbproject.ownpli.repository;

import dbproject.ownpli.domain.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * [update] 닉네임 변경
     * @param name
     * @param userId
     * @return int
     * @CRUD update
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE UserEntity u SET u.name = :name where u.userId = :userId")
    int updateUserName(@Param("name") String name, @Param("userId") String userId);


}

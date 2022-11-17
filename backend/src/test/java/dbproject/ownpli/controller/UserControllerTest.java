package dbproject.ownpli.controller;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void insertTest() {
        final UserEntity user = UserEntity.builder()
            .userId("testuser@naver.com")
            .nickname("test user")
            .passward("1234")
            .age(14)
            .sex(1)
            .build();

        userRepository.save(user);
    }

}

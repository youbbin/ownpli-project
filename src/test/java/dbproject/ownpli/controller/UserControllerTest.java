package dbproject.ownpli.controller;

import dbproject.ownpli.domain.User;
import dbproject.ownpli.domain.value.Sex;
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
        final User user = User.builder()
            .userId("testuser@naver.com")
            .name("test user")
            .password("1234")
            .age(14)
            .sex(Sex.MALE)
            .build();

        userRepository.save(user);
    }

}

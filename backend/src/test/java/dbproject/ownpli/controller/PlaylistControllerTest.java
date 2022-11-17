package dbproject.ownpli.controller;

import dbproject.ownpli.repository.UserRepository;
import dbproject.ownpli.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaylistControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaylistService playlistService;

//    @Test
//    public void findAllPlaylists(String) {
//        final UserEntity user = UserEntity.builder()
//            .userId("testuser@naver.com")
//            .nickname("test user")
//            .passward("1234")
//            .age(14)
//            .sex(1)
//            .build();
//
//        userRepository.save(user);
//
//        final PlaylistEntity playlist = PlaylistEntity.builder()
//            .playlistId("123")
//            .playlistTitle("123")
//            .userId("testuser@naver.com")
//
//        userRepository.save(user);
//        List<PlaylistEntity> playlistEntities = playlistService.findPlaylistByUserId(playlistId);
//
//    }
}

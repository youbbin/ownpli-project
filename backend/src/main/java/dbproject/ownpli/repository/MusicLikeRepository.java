package dbproject.ownpli.repository;

import dbproject.ownpli.domain.User;
import dbproject.ownpli.domain.Music;
import dbproject.ownpli.domain.MusicLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicLikeRepository extends JpaRepository<MusicLike, Long> {

    Long countByMusic(Music music);

    Boolean existsByMusicAndUser(Music music, User user);

    void deleteByMusicAndUser(Music music, User user);

}

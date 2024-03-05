package dbproject.ownpli.repository;

import dbproject.ownpli.domain.User;
import dbproject.ownpli.domain.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {

    List<Playlist> findByUser(User user);

    Optional<Playlist> findFirstByOrderByPlaylistIdDesc();

    Optional<Playlist> findByPlaylistTitleAndUser(String title, User user);

    Boolean existsByPlaylistTitleAndUser(String title, User user);

}

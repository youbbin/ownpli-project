package dbproject.ownpli.repository;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String> {

    List<PlaylistEntity> findByUserEntity(UserEntity userEntity);

    Optional<PlaylistEntity> findFirstByOrderByPlaylistIdDesc();

    Optional<PlaylistEntity> findByPlaylistTitleAndUserEntity(String title, UserEntity userEntity);

    Boolean existsByPlaylistTitleAndUserEntity(String title, UserEntity userEntity);

}

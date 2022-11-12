package dbproject.ownpli.repository;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String> {

    @Query("SELECT p FROM PlaylistEntity p WHERE p.userId = :id")
    PlaylistEntity findByUserId(String id);
}

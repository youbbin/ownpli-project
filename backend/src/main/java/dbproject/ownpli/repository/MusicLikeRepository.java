package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicLikeRepository extends JpaRepository<MusicLikeEntity, Long> {
}

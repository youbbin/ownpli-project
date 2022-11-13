package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicMoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicMoodRepository extends JpaRepository<MusicMoodEntity, Long> {
}

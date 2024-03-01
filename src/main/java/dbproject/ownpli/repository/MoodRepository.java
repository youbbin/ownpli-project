package dbproject.ownpli.repository;

import dbproject.ownpli.domain.MoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodRepository extends JpaRepository<MoodEntity, Long> {

    MoodEntity findMoodEntityByMood(String mood);
}

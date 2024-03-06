package dbproject.ownpli.repository;

import dbproject.ownpli.domain.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> {

    Mood findMoodEntityByMood(String mood);
}

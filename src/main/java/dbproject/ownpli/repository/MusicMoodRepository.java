package dbproject.ownpli.repository;

import dbproject.ownpli.domain.Mood;
import dbproject.ownpli.domain.MusicMood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicMoodRepository extends JpaRepository<MusicMood, Long> {

    List<MusicMood> findByMood(Mood mood);

}

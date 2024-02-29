package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MoodEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodRepository extends JpaRepository<MoodEntity, Long> {

    MoodEntity findMoodEntityByMood(String mood);
}

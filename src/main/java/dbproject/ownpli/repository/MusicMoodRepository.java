package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MoodEntity;
import dbproject.ownpli.domain.music.MusicMoodEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicMoodRepository extends JpaRepository<MusicMoodEntity, Long> {

    List<MusicMoodEntity> findByMoodEntity(MoodEntity moodEntity);

}

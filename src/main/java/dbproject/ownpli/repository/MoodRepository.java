package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MoodEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodRepository extends JpaRepository<MoodEntity, Long> {

    @Query(value = "SELECT m FROM MoodEntity m WHERE m.moodId in :moodId", nativeQuery = true)
    List<MoodEntity> findByIds(@Param("moodId") List<Long> moodId);
}

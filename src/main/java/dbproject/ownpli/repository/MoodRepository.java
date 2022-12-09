package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MoodEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodRepository extends JpaRepository<MoodEntity, Long> {

    /**
     * [select] moodId 리스트로 moodName 리스트 출력
     * @param moodNum
     * @return List[String]
     * @CRUD read
     */
    @Query(value = "SELECT m.mood FROM mood m WHERE m.moodNum in :moodNum")
    List<String> findByIds(@Param("moodNum") List<Long> moodNum);

    @Query("SELECT m.moodNum from mood m WHERE m.mood in :mood")
    List<Long> findMoodEntitiesByMood(List<String> mood);

    Long findMoodEntityByMood(String mood);
}

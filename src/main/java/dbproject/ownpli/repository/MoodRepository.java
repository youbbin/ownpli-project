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
     * @param moodId
     * @return List[String]
     */
    @Query(value = "SELECT m.moodName FROM MoodEntity m WHERE m.moodId in :moodId", nativeQuery = true)
    List<String> findByIds(@Param("moodId") List<Long> moodId);
}

package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicMoodEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicMoodRepository extends JpaRepository<MusicMoodEntity, Long> {

    /**
     * [select] musicId로 mood 리스트 조회
     * @param musicIds
     * @return List[Long]
     * @CRUD read
     */
    @Query(value = "SELECT m.moodNum FROM music_mood m WHERE m.musicId in :musicIds")
    List<Long> findByMusicId(@Param("musicIds") String musicIds);

    /**
     * [select] 무드 이름으로 music-mood 엔티티 출력
     * @param moodNum
     * @return List [MoodEntity]
     * @CRUD read
     */
    @Query("SELECT mm.musicId FROM music_mood mm WHERE mm.moodNum in :moodNum")
    List<String> findByMoodNum(List<Long> moodNum);

    @Query("SELECT mm.musicId FROM music_mood mm WHERE mm.moodNum = :moodNum")
    List<String> findOneByMoodNum(Long moodNum);
}

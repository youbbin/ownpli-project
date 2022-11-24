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
    @Query(value = "SELECT m.moodNum FROM music-mood m WHERE m.musicId in :musicIds")
    List<Long> findByMusicId(@Param("musicIds") String musicIds);
}

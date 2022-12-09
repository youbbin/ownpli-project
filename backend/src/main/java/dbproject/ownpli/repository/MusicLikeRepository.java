package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicLikeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicLikeRepository extends JpaRepository<MusicLikeEntity, Long> {

    /**
     * [select] 음악의 총 좋아요 수
     * @param musicId
     * @return Long
     * @CRUD read
     */
    @Query("SELECT count(ml.musicId) FROM music_like ml WHERE ml.musicId = :musicId")
    Optional<Long> countByMusicId(@Param("musicId") String musicId);

    /**
     * [select] 사용자가 좋아요한 노래 목록
     * @param userId
     * @return List[String]
     * @CRUD read
     */
    @Query(value = "SELECT ml.musicId FROM music_like ml WHERE ml.userId = :userId")
    List<String> findByUserId(@Param("userId") String userId);
}

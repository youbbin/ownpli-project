package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, String> {

    /**
     * [select] singerId로 음악 정보 출력
     * @param singerId
     * @return
     */
    @Query(value = "SELECT m FROM MusicEntity m WHERE m.singerId in :singerId", nativeQuery = true)
    List<MusicEntity> findBySingerId(@Param("singerId") String singerId);

    /**
     * [select] musicId 리스트로 해당하는 음악 출력
     * @param musicId
     * @return
     */
    @Query(value = "SELECT m FROM MusicEntity m WHERE m.musicId in :musicId", nativeQuery = true)
    List<MusicEntity> findByMusicIds(@Param("musicId") List<String> musicId);

    /**
     * [select] 단일 음악 장르 출력
     * @param musicId
     * @return
     */
    @Query(value = "SELECT m.genre FROM MusicEntity m WHERE m.musicId = :musicId", nativeQuery = true)
    Long findGenreByMusicId(@Param("musicId") String musicId);

}

package dbproject.ownpli.repository;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String> {

    /**
     * [select] userId로 플레이리스트 조회
     * @param id
     * @return PlaylistEntity
     * @CRUD read
     */
    @Query(value = "SELECT p FROM playlist p WHERE p.userId = :id")
    List<PlaylistEntity> findByUserId(String id);

    @Query(value = "SELECT p FROM playlist p WHERE p.playlistId in :id")
    List<PlaylistEntity> findAllByPlaylistId(List<String> id);



    /**
     * [select] 가장 마지막 행 가져오기
     * @return PlaylistEntity
     */
    Optional<PlaylistEntity> findFirstByOrderByPlaylistIdDesc();

    /**
     * userId와 제목을 이용해 플레이리스트 엔티티 찾기
     * @param playlistTitle
     * @param userId
     * @return
     */
    @Query("SELECT p.playlistId FROM playlist p WHERE p.userId = :userId AND p.playlistTitle in :playlistTitle")
    Optional<List<String>> findPlaylistIdsByPlaylistTitleAndUserId(List<String> playlistTitle, String userId);

    Optional<PlaylistEntity> findByPlaylistTitleAndUserId(String title, String userId);

    /**
     * 플레이리스트 타이틀 변경
     * @param oldTitle
     * @param newTitle
     * @param userId
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE playlist p SET p.playlistTitle = :newTitle where p.userId = :userId and p.playlistTitle = :oldTitle")
    int updatePlaylistTitle(@Param("oldTitle") String oldTitle, @Param("newTitle") String newTitle, @Param("userId") String userId);

}

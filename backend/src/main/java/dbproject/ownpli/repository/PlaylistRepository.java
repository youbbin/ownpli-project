package dbproject.ownpli.repository;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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

    /**
     * [select] 가장 마지막 행 가져오기
     * @param userId
     * @return PlaylistEntity
     */
    Optional<PlaylistEntity> findTop1ByUserIdOrderByPlaylistIdDesc(String userId);

//    오류!
//    /**
//     * [select] fk(musicId)값으로 플레이리스트 조회하기
//     * @param musicId
//     * @return
//     */
//    List<MusicEntity> findByMusic_MusicId(String musicId);

}

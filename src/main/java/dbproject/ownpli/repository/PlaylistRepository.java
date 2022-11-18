package dbproject.ownpli.repository;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String> {

    /**
     * [select] userId로 플레이리스트 조회
     * @param id
     * @return PlaylistEntity
     */
    @Query(value = "SELECT p FROM PlaylistEntity p WHERE p.userId = :id", nativeQuery = true)
    PlaylistEntity findByUserId(String id);

//    오류!
//    /**
//     * [select] fk(musicId)값으로 플레이리스트 조회하기
//     * @param musicId
//     * @return
//     */
//    List<MusicEntity> findByMusic_MusicId(String musicId);

}

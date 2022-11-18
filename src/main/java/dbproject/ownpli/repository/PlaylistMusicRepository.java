package dbproject.ownpli.repository;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, String> {

    /**
     * [select] 플레이리스트 아이디로 해당 컬럼 출력
     * @param playlistId
     * @return List[PlaylistEntity]
     */
    @Query(value = "SELECT pm FROM PlaylistMusicEntity pm WHERE pm.playlistId = :playlistId", nativeQuery = true)
    List<PlaylistEntity> findByPlaylistId(@Param("playlistId") List<String> playlistId);

    /**
     * [select] 플레이리스트 아이디로 음악 id만 출력
     * @param playlistId
     * @return List[String]
     */
    @Query(value = "SELECT pm.musicId FROM PlaylistMusicEntity pm WHERE pm.playlistId = :playlistId", nativeQuery = true)
    List<String> findMusicIdsByPlaylistId(String playlistId);

    /**
     * [select] 플레이리스트 아이디로 playlistMusicEntity 출력
     * @param playlistId
     * @return List[PlaylistMusicEntity]
     */
    @Query(value = "SELECT pm FROM PlaylistMusicEntity pm join fetch pm.musicId WHERE :playlistId", nativeQuery = true)
    List<PlaylistMusicEntity> findAllByPlaylistId(String playlistId);

//    /**
//     * [select] fk(musicId)값으로 playlistMusic 조회하기
//     * @param id
//     * @return
//     */
//    List<MusicEntity> findByMusic_MusicId(String id);
}

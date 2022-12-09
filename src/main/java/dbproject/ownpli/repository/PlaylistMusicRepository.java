package dbproject.ownpli.repository;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, String> {

    /**
     * [select] 플레이리스트 아이디들로 해당 컬럼 출력
     * @param playlistId
     * @return List[PlaylistEntity]
     * @CRUD read
     */
    @Query(value = "SELECT pm FROM playlist_music pm WHERE pm.playlistId in :playlistId")
    List<PlaylistEntity> findByPlaylistId(@Param("playlistId") List<String> playlistId);

    /**
     * [select] 플레이리스트 아이디로 음악 id만 출력
     * @param playlistId
     * @return List[String]
     * @CRUD read
     */
    @Query(value = "SELECT pm.musicId FROM playlist_music pm WHERE pm.playlistId = :playlistId")
    List<String> findMusicIdsByPlaylistId(String playlistId);

    /**
     * [select] 플레이리스트 아이디로 playlistMusicEntity 출력
     * @param playlistId
     * @return List[PlaylistMusicEntity]
     * @CRUD read
     */
    @Query(value = "SELECT pm FROM playlist_music pm WHERE pm.playlistId = :playlistId")
    List<PlaylistMusicEntity> findAllByPlaylistId(String playlistId);

    /**
     * 음악 아이디 count
     * @param musicId
     * @return
     */
    @Query("SELECT count(pm.musicId) FROM playlist_music pm WHERE pm.musicId = :musicId GROUP BY pm.musicId")
    Long countByMusicId(String musicId);

    @Query("SELECT DISTINCT pm.musicId FROM playlist_music pm")
    Optional<List<String>> findDistinctMusicId();

}

package dbproject.ownpli.repository;

import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, String> {

    /**
     * [select] 플레이리스트 아이디들로 음악 리스트 출력
     * @param playlistId
     * @return List[PlaylistEntity]
     * @CRUD read
     */
    @Query(value = "SELECT distinct pm.musicId FROM playlist_music pm WHERE pm.playlistId in :playlistId")
    List<String> findByPlaylistId(@Param("playlistId") List<String> playlistId);

    /**
     * [select] 플레이리스트 아이디로 음악 id만 출력
     * @param playlistId
     * @return List[String]
     * @CRUD read
     */
    @Query(value = "SELECT DISTINCT pm.musicId FROM playlist_music pm WHERE pm.playlistId = :playlistId")
    List<String> findMusicIdsByPlaylistId(String playlistId);

    @Query("SELECT pm.key FROM playlist_music pm WHERE pm.playlistId = :playlistId AND pm.musicId in :musicIds")
    List<Long> findPlaylistMusicIdsByTitleAndMusicId(String playlistId, List<String> musicIds);

    /**
     * [select] 플레이리스트 아이디로 playlistMusicEntity 출력
     * @param playlistId
     * @return List[PlaylistMusicEntity]
     * @CRUD read
     */
    @Query(value = "SELECT pm FROM playlist_music pm WHERE pm.playlistId in :playlistId")
    List<PlaylistMusicEntity> findAllByPlaylistId(List<String> playlistId);

    @Query(value = "SELECT pm FROM playlist_music pm WHERE pm.playlistId in :playlistId")
    PlaylistMusicEntity findOneByPlaylistId(List<String> playlistId);

//    /**
//     * 음악 아이디 count
//     * @param musicId
//     * @return
//     */
//    @Query("SELECT count(pm.musicId) FROM playlist_music pm WHERE pm.musicId = :musicId GROUP BY pm.musicId")
//    Long countByMusicId(String musicId);

    /**
     * playlist에 많이 담긴 순으로
     * @return
     */
    @Query("SELECT DISTINCT pm.musicId FROM playlist_music pm GROUP BY pm.musicId ORDER BY count(pm.musicId) DESC")
    Optional<List<String>> findDistinctMusicId();

    @Modifying
    @Transactional
    @Query("DELETE FROM playlist_music pm WHERE pm.key in :playlistMusicKeys")
    void deleteAllById(List<Long> playlistMusicKeys);
}

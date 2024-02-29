package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, String> {

    /**
     * playlist에 많이 담긴 순으로
     * @return
     */
    @Query("SELECT DISTINCT pm.musicId FROM playlist_music pm GROUP BY pm.musicId ORDER BY count(pm.musicId) DESC")
    Optional<List<String>> findDistinctMusicId();

    void deleteAllByPlaylistEntityIn(List<PlaylistEntity> playlistEntities);

    void deleteAllByPlaylistEntityAndMusicEntityIn(PlaylistEntity playlistEntity, List<MusicEntity> musicEntity);
}

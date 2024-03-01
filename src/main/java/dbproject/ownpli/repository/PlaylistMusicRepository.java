package dbproject.ownpli.repository;

import dbproject.ownpli.domain.MusicEntity;
import dbproject.ownpli.domain.PlaylistEntity;
import dbproject.ownpli.domain.PlaylistMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, String> {

    void deleteAllByPlaylistEntityIn(List<PlaylistEntity> playlistEntities);

    void deleteAllByPlaylistEntityAndMusicEntityIn(PlaylistEntity playlistEntity, List<MusicEntity> musicEntity);
}

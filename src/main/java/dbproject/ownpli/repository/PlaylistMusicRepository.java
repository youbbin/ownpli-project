package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, String> {

    void deleteAllByPlaylistEntityIn(List<PlaylistEntity> playlistEntities);

    void deleteAllByPlaylistEntityAndMusicEntityIn(PlaylistEntity playlistEntity, List<MusicEntity> musicEntity);
}

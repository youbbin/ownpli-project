package dbproject.ownpli.repository;

import dbproject.ownpli.domain.Music;
import dbproject.ownpli.domain.Playlist;
import dbproject.ownpli.domain.PlaylistMusic;
import dbproject.ownpli.repository.querydsl.CustomPlaylistMusicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, String>, CustomPlaylistMusicRepository {

    void deleteAllByPlaylistIn(List<Playlist> playlistEntities);

    void deleteAllByPlaylistAndMusicIn(Playlist playlist, List<Music> music);
}

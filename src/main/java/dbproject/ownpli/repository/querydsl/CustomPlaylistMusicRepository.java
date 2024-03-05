package dbproject.ownpli.repository.querydsl;

import dbproject.ownpli.domain.PlaylistMusic;
import dbproject.ownpli.domain.User;

import java.util.List;

public interface CustomPlaylistMusicRepository {

    List<PlaylistMusic> findAgeCompare(User user);

}

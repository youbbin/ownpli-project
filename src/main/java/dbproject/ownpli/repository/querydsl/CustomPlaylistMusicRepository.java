package dbproject.ownpli.repository.querydsl;

import dbproject.ownpli.domain.PlaylistMusicEntity;
import dbproject.ownpli.domain.UserEntity;

import java.util.List;

public interface CustomPlaylistMusicRepository {

    List<PlaylistMusicEntity> findAgeCompare(UserEntity user);

}

package dbproject.ownpli.service;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public List<PlaylistEntity> findPlaylistByUserId(String userId) {
        return (List<PlaylistEntity>) playlistRepository.findByUserId(userId);
    }
}

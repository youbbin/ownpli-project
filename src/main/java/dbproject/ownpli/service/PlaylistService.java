package dbproject.ownpli.service;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.repository.MusicRepository;
import dbproject.ownpli.repository.PlaylistMusicRepository;
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
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;

    /**
     * 유저이메일로 플레이리스트 목록 찾기
     * @param userId
     * @return
     */
    public List<PlaylistEntity> findPlaylistByUserId(String userId) {
        return (List<PlaylistEntity>) playlistRepository.findByUserId(userId);
    }

    /**
     * playlistID로 음악 리스트 찾기
     * @param playlistId
     * @return
     */
    public List<MusicEntity> findMusicsByPlaylistId(String playlistId) {
        List<String> musicIds = playlistMusicRepository.findMusicIdsByPlaylistId(playlistId);
        List<MusicEntity> musics = musicRepository.findByMusicId(musicIds);

        return musics;
    }

    /**
     * 새 playlist 저장
     * @param playlist
     */
    public void savePlaylist(PlaylistEntity playlist) {
        playlistRepository.save(playlist);
    }

}

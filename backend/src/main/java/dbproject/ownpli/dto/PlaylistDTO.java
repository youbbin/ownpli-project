package dbproject.ownpli.dto;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
    private String playlistId;
    private String title;
    private String userId;
    private List<MusicDTO> musicDTOs;
    private Date date;

    public static PlaylistDTO from(PlaylistEntity playlist, List<MusicDTO> musicDTOList, Date date) {
        return new PlaylistDTO(
            playlist.getPlaylistId(),
            playlist.getPlaylistTitle(),
            playlist.getUserId(),
            musicDTOList,
            date);
    }
}

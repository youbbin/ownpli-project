package dbproject.ownpli.dto;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
    private String playlistId;
    private String title;
    private String userId;
    private Date date;

    public static PlaylistDTO from(PlaylistEntity playlist, Date date) {
        return new PlaylistDTO(
            playlist.getPlaylistId(),
            playlist.getPlaylistTitle(),
            playlist.getUserId(),
            date);
    }
}

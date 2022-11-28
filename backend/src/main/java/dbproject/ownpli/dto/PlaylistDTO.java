package dbproject.ownpli.dto;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
    private String playlistId;
    private String title;
    private String userId;
    private LocalDate date;

    public static PlaylistDTO from(PlaylistEntity playlist, LocalDate date) {
        return new PlaylistDTO(
            playlist.getPlaylistId(),
            playlist.getPlaylistTitle(),
            playlist.getUserId(),
            date);
    }
}

package dbproject.ownpli.controller.dto.playlist;

import dbproject.ownpli.domain.Playlist;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaylistDTO {
    private String playlistId;
    private String title;
    private String userId;

    public static PlaylistDTO from(Playlist playlist) {
        return PlaylistDTO.builder()
                .playlistId(playlist.getPlaylistId())
                .title(playlist.getPlaylistTitle())
                .userId(playlist.getUser().getUserId())
                .build();
    }
}

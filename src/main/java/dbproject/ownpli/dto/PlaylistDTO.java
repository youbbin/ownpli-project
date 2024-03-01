package dbproject.ownpli.dto;

import dbproject.ownpli.domain.playlist.PlaylistEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaylistDTO {
    private String playlistId;
    private String title;
    private String userId;

    public static PlaylistDTO from(PlaylistEntity playlist) {
        return PlaylistDTO.builder()
                .playlistId(playlist.getPlaylistId())
                .title(playlist.getPlaylistTitle())
                .userId(playlist.getUserEntity().getUserId())
                .build();
    }
}

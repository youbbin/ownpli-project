package dbproject.ownpli.controller.dto.playlist;

import lombok.Getter;

@Getter
public class PlaylistUpdateRequest {

    private String userId;
    private String oldTitle;
    private String newTitle;

}

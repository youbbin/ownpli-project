package dbproject.ownpli.dto;

import lombok.Getter;

@Getter
public class PlaylistUpdateRequest {

    private String userId;
    private String oldTitle;
    private String newTitle;

}

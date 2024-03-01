package dbproject.ownpli.controller.dto.playlist;

import lombok.Getter;

import java.util.List;


@Getter
public class PlaylistCreateRequest {

    private String userId;
    private String title;
    private List<String> songIds;

}

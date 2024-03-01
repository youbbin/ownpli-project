package dbproject.ownpli.dto;

import lombok.Getter;

import java.util.List;


@Getter
public class PlaylistCreateRequest {

    private String userId;
    private String title;
    private List<String> songIds;

}

package dbproject.ownpli.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistMusicDeleteRequest {

    List<Long> musicIds;

}

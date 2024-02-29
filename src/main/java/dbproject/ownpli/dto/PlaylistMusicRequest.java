package dbproject.ownpli.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistMusicRequest {

    List<Long> musicIds;

}

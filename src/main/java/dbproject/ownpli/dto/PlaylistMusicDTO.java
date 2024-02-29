package dbproject.ownpli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistMusicDTO {
    private PlaylistDTO playlistDTO;
    private List<MusicResponse> musicDTOList;

    public static PlaylistMusicDTO from(PlaylistDTO playlistDTO, List<MusicResponse> musicDTOList) {
        return new PlaylistMusicDTO(playlistDTO, musicDTOList);
    }
}

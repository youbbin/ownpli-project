package dbproject.ownpli.controller.dto.playlist;

import dbproject.ownpli.controller.dto.music.MusicResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaylistMusicDTO {

    private PlaylistDTO playlistDTO;
    private List<MusicResponse> musicDTOList;

    public static PlaylistMusicDTO from(PlaylistDTO playlistDTO, List<MusicResponse> musicDTOList) {
        return PlaylistMusicDTO.builder()
                .playlistDTO(playlistDTO)
                .musicDTOList(musicDTOList)
                .build();
    }
}

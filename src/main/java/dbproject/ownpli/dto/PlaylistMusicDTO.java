package dbproject.ownpli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

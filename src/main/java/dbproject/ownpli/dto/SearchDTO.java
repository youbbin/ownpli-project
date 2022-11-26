package dbproject.ownpli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private List<MusicDTO> titles;
    private List<MusicDTO> singers;

    public static SearchDTO from(List<MusicDTO> titles, List<MusicDTO> singers) {
        return new SearchDTO(titles, singers);
    }
}

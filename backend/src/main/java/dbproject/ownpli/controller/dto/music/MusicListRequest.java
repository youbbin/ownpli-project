package dbproject.ownpli.controller.dto.music;

import lombok.Getter;

import java.util.List;

@Getter
public class MusicListRequest {

    private List<String> likedSinger;
    private List<String> dislikedSinger;
    private List<String> country;
    private List<String> year;
    private List<Long> genre;
    private List<Long> mood;

}

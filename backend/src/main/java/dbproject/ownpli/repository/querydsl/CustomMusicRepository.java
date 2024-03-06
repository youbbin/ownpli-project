package dbproject.ownpli.repository.querydsl;

import dbproject.ownpli.controller.dto.music.MusicListRequest;
import dbproject.ownpli.domain.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomMusicRepository {

    Page<Music> findDynamicQueryAdvance(MusicListRequest request, Pageable pageable);

    List<Music> searchTitleAndSinger(String search);

}

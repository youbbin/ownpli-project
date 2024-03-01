package dbproject.ownpli.repository.querydsl;

import dbproject.ownpli.controller.dto.music.MusicListRequest;
import dbproject.ownpli.domain.MusicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomMusicRepository {

    Page<MusicEntity> findDynamicQueryAdvance(MusicListRequest request, Pageable pageable);

    List<MusicEntity> searchTitleAndSinger(String search);

}

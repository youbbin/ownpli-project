package dbproject.ownpli.repository;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicLikeRepository extends JpaRepository<MusicLikeEntity, Long> {

    Long countByMusicEntity(MusicEntity musicEntity);

    Boolean existsByMusicEntityAndUserEntity(MusicEntity musicEntity, UserEntity userEntity);

    void deleteByMusicEntityAndUserEntity(MusicEntity musicEntity, UserEntity userEntity);

}

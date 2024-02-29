package dbproject.ownpli.repository;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicLikeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicLikeRepository extends JpaRepository<MusicLikeEntity, Long> {

    Long countByMusicEntity(MusicEntity musicEntity);

    Boolean existsByMusicEntityAndUserEntity(MusicEntity musicEntity, UserEntity userEntity);

    void deleteByMusicEntityAndUserEntity(MusicEntity musicEntity, UserEntity userEntity);


    List<MusicLikeEntity> findByUserEntity(UserEntity userEntity);
}

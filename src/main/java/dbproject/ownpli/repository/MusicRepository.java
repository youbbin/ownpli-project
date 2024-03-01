package dbproject.ownpli.repository;

import dbproject.ownpli.domain.MusicEntity;
import dbproject.ownpli.repository.querydsl.CustomMusicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, String>, CustomMusicRepository {

}

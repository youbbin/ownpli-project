package dbproject.ownpli.repository;

import dbproject.ownpli.domain.SingerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingerRepository extends JpaRepository<SingerEntity, String> {
}

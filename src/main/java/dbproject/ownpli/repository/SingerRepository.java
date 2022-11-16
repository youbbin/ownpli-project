package dbproject.ownpli.repository;

import dbproject.ownpli.domain.SingerEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SingerRepository extends JpaRepository<SingerEntity, String> {

    @Query(value = "SELECT s FROM SingerEntity s WHERE s.singerName = :name", nativeQuery = true)
    SingerEntity findBySingerName(@Param("name") String SingerName);
}

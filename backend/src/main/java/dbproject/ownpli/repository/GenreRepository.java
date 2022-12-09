package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.GenreEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    /**
     * [SELECT] 장르 이름으로 장르 아이디 출력
     * @param genre
     * @return List [GenreEntity]
     * @CRUD SELECT
     */
    @Query("SELECT g.genreNum FROM genre g WHERE g.genre in :genre")
    List<Long> findGenreNumsByGenre(@Param("genre") List<String> genre);
}

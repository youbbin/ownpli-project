package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.music.MusicMoodEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {

    /**
     * 제목으로 음악 엔티티 찾기
     * @param title
     * @return
     */
    @Query("SELECT m.musicId FROM MusicEntity m WHERE m.title in :title")
    List<String> findMusicIdsByTitle(@Param("title") List<String> title);


    Optional<MusicEntity> findMusicEntityByTitleContainingIgnoreCase(String title);

    @Query("SELECT distinct m.singer FROM MusicEntity m")
    List<String> findSingers();

    @Query("SELECT m.musicId FROM MusicEntity m ORDER BY m.date DESC ")
    List<String> findMusicIdsOrderByYear();

    /**
     * [select] 음악 아이디로 mp3파일 위치 가져오기
     * @param musicId
     * @return String
     * @CRUD read
     */
    @Query(value = "SELECT m.mp3File FROM MusicEntity m WHERE m.musicId = :musicId")
    String findMp3FileByMusicId(@Param("musicId") String musicId);

    /**
     * [select] 음악 제목으로 음악 리스트 검색
     * @param title
     * @return List[MusicEntity]
     * @CRUD read
     * @container
     * - `findBy`로 시작하면 select 쿼리를 시작한다는 뜻입니다.
     * - `Title, Singer`는 이 컬럼에서 파라미터로 받은 값을 찾겠다는 의미입니다. (엔티티 클래스의 컬럼 참고)
     * - `Containing`이 없다면 해당 키워드와 일치하는 결과만 찾고, 이 키워드가 있는 경우는 포함하는 결과를 찾습니다. 즉, SQL문의 like %xx% 와 비슷합니다.
     * - `IgnoreCase` 키워드는 대소문자 구별을 하지 않는다는 의미입니다. 이것이 없다면 대소문자가 구별됩니다.
     */
    List<MusicEntity> findByTitleContainingIgnoreCase(String title);

    /**
     * [select] 가수 이름으로 음악 리스트 검색
     * @param singer
     * @return List[MusicEntity]
     * @CRUD read
     * @container
     * - `findBy`로 시작하면 select 쿼리를 시작한다는 뜻입니다.
     * - `Title, Singer`는 이 컬럼에서 파라미터로 받은 값을 찾겠다는 의미입니다. (엔티티 클래스의 컬럼 참고)
     * - `Containing`이 없다면 해당 키워드와 일치하는 결과만 찾고, 이 키워드가 있는 경우는 포함하는 결과를 찾습니다. 즉, SQL문의 like %xx% 와 비슷합니다.
     * - `IgnoreCase` 키워드는 대소문자 구별을 하지 않는다는 의미입니다. 이것이 없다면 대소문자가 구별됩니다.
     */
    List<MusicEntity> findBySingerContainingIgnoreCase(String singer);

}

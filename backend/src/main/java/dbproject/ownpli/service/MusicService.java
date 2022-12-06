package dbproject.ownpli.service;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;
    private final MusicMoodRepository musicMoodRepository;
    private final MusicLikeRepository musicLikeRepository;
    private final GenreRepository genreRepository;
    private final MoodRepository moodRepository;
    private final QueryRepository queryRepository;

    /**
     * 모든 음악리스트 찾기(DTO)
     * @return
     */
    public List<MusicDTO> findAllMusics() {
        List<MusicEntity> all = musicRepository.findAll();
        List<MusicDTO> models = new ArrayList<>();

        for(int i = 0; i < all.size(); i++) {
            models.add(findMusicInfo(all.get(i).getMusicId()));
        }
        return models;
    }

    /**
     * MusicEntity 리스트를 MusicDTO로 변환
     * @param musicEntities
     * @return
     */
    public List<MusicDTO> musicEntitiesToMusicDTO(List<MusicEntity> musicEntities) {
        List<MusicDTO> models = new ArrayList<>();

        for(int i = 0; i < musicEntities.size(); i++) {
            models.add(findMusicInfo(musicEntities.get(i).getMusicId()));
        }
        return models;
    }

    /**
     * 모든 음악리스트 찾기(DTO)
     * @return
     */
    public List<MusicEntity> findAll() {
        List<MusicEntity> all = musicRepository.findAll();

        return all;
    }

    /**
     * 음악 아이디로 음악 엔티티 찾기
     * @param musicId
     * @return
     */
    public MusicEntity findByMusicId(String musicId) {
        return musicRepository.findById(musicId).get();
    }

    /**
     * 음악 이름으로 음악 아이디들 찾기
     * @param title
     * @return
     */
    public List<String> findByTitle(List<String> title) {
        return musicRepository.findMusicIdsByTitle(title);
    }

    /**
     * 음악 이름으로 음악 리스트 검색
     * @param title
     * @return
     */
    public List<MusicEntity> findByTitleContain(String title) {
        return musicRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * 가수 이름으로 음악 리스트 검색
     * @param singer
     * @return
     */
    public List<MusicEntity> findBySingerContain(String singer) {
        return musicRepository.findBySingerContainingIgnoreCase(singer);
    }

    /**
     * 사용자가 좋아요한 음악 리스트 출력
     * @param userId
     * @return
     */
    public List<MusicEntity> findMusicListByUserId(String userId) {
        List<String> musicIds = musicLikeRepository.findByUserId(userId);
        return musicRepository.findByMusicId(musicIds);
    }

    /**
     * 단일 음악 정보 보내기
     * @param musicId
     * @return MusicDTO
     * @throws IOException
     */
    public MusicDTO findMusicInfo(String musicId) {
        log.info("musicId = " + musicId);
        MusicEntity byMusicId = findByMusicId(musicId);
        String byGenreId = findByGenreId(byMusicId.getGenreNum());

        Optional<Long> aLong = musicLikeRepository.countByMusicId(musicId);
        Long likes = Long.valueOf(0);
        if (!aLong.isEmpty()) likes = aLong.get();

        String inputFile = byMusicId.getImageFile();

        inputFile = inputFile.replaceFirst("D", "C");

        Path path = new File(inputFile).toPath();
        FileSystemResource resource = new FileSystemResource(path);

        log.info("byMusicId.getMusicId = " + byMusicId.getMusicId());

        return MusicDTO.from(byMusicId, byGenreId, likes, resource);
    }

    /**
     * 음악 필터링해서 추가
     * @param param
     * @return
     * @throws ParseException
     */
    public List<MusicDTO> addMusics(LinkedHashMap param) throws ParseException {
        List<Long> genre, mood;
        Optional g = Optional.ofNullable(param.get("genre"));
        if(g.isEmpty()) genre = null;
        else genre = genreRepository.findGenreNumsByGenre(divString(g.get().toString()));

        List<MusicEntity> musicEntities = filteringMusics(param, genre);

        Optional m = Optional.ofNullable(param.get("mood"));
        if(m.isEmpty()) mood = null;
        else mood = findMoodEntitiesByMood(divString(m.get().toString()));

        if(mood == null) return musicEntitiesToMusicDTO(musicEntities);
        else return findMusicsByMoodIds(mood, musicEntities);
    }

    /**
     * 동적 query QueryDSL 검색 필터링
     * @param param
     * @param genre
     * @return MusicEntity List
     * @throws ParseException
     */
    private List<MusicEntity> filteringMusics(LinkedHashMap param, List<Long> genre) throws ParseException {
        List<String> likes, hates, crty, year;

        Optional l = Optional.ofNullable(param.get("likedSinger"));
        Optional h = Optional.ofNullable(param.get("dislikedSinger"));
        Optional c = Optional.ofNullable(param.get("country"));
        Optional y = Optional.ofNullable(param.get("year"));

        if(l.isEmpty()) {
            likes = null;
            log.info("likes=null");
        }
        else likes = divString(l.get().toString());

        if(h.isEmpty()) {
            hates = null;
            log.info("hates=null");
        }
        else hates = divString(h.get().toString());

        if(c.isEmpty()) {
            crty = null;
            log.info("langs=null");
        }
        else crty = divString(c.get().toString());

        if(y.isEmpty()) {
            year = null;
            log.info("year=null");
        }
        else year = divString(y.get().toString());

        return queryRepository.findDynamicQueryAdvance(likes, hates, genre, crty, year);
    }

    public List<String> divString(String s) {
        StringTokenizer st = new StringTokenizer(s, ",");

        List<String> list = new ArrayList<>();
        for(int i = 0; i < st.countTokens() + 1; i++) {
            list.add(st.nextToken());
        }
        return list;
    }

    /**
     * 플레이리스트에서 뮤직 정보 가져오기
     * @param musicIds
     * @return
     */
    public List<MusicDTO> findMusicInfosByPlaylist(List<String> musicIds) {
        List<MusicDTO> arr = new ArrayList<>();

        for(int i = 0; i < musicIds.size(); i++) {
            arr.add(findMusicInfo(musicIds.get(i)));
        }

        return arr;
    }

    /**
     * 장르 아이디로 장르 이름 출력
     * @param genreId
     * @return String
     */
    public String findByGenreId(Long genreId) {
        return genreRepository.findById(genreId).get().getGenre();
    }

    /**
     * 무드아이디 리스트와 음악 엔티티들을 이용해 최종 정보 출력
     * @param moods
     * @param musicEntities
     * @return
     */
    public List<MusicDTO> findMusicsByMoodIds(List<Long> moods, List<MusicEntity> musicEntities) {
        if(moods.isEmpty() && musicEntities.isEmpty())
            return findAllMusics();
        else if(moods.isEmpty() && !musicEntities.isEmpty())
            return musicEntitiesToMusicDTO(musicEntities);
        else if(musicEntities.isEmpty()) musicEntities = findAll();

        List<String> byMoodNum = musicMoodRepository.findByMoodNum(moods);
        Set<String> set = new HashSet<>(byMoodNum);     //중복제거
        List<String> musicIds = new ArrayList<>();       //musicId를 담을 리스트
        List<MusicDTO> musics = new ArrayList<>();      //음악 정보 리스트

        for(int i = 0; i < byMoodNum.size(); i++)
            if(Collections.frequency(byMoodNum, set.toArray()[i].toString()) == moods.size()) {
                musicIds.add(set.toArray()[i].toString());
            }

        for(int i = 0; i < musicEntities.size(); i++)
            for(int j = 0; j < musicIds.size(); j++)
                if(musicEntities.get(i).getMusicId().equals(musicIds))
                    musics.add(findMusicInfo(musicEntities.get(i).getMusicId()));

        return musics;
    }

    /**
     * musicId로 txt파일에서 가사 불러오기
     * @param musicId
     * @return Model
     * @throws IOException
     */
    public String readLirics(String musicId) throws IOException {
        //파일 읽기
        BufferedReader br = new BufferedReader(new FileReader(musicRepository.findById(musicId).get().getLyricsFile()));
        String line ="", result = "";

        //한 줄 씩 읽은 내용 쓰기
        while( (line=br.readLine()) != null ) {
            result += line;
            result += "\n";
        }

        return result;
    }

    /**
     * 장르 이름 리스트로 장르 아이디 리스트 찾기
     * @param genre
     * @return
     */
    public Optional<List<Long>> findGenresByGenre(List<String> genre) {
        return Optional.ofNullable(genreRepository.findGenreNumsByGenre(genre));
    }

    /**
     * mood 이름 리스트로 mood 아이디 리스트 찾기
     * @param mood
     * @return
     */
    public List<Long> findMoodEntitiesByMood(List<String> mood) {
        return moodRepository.findMoodEntitiesByMood(mood);
    }

    /**
     * 음악 아이디로 mood 조회
     * @param musicId
     * @return List[String]
     */
    public List<String> findMoodByMusicId(String musicId) {
        List<Long> byMusicId = musicMoodRepository.findByMusicId(musicId);
        return moodRepository.findByIds(byMusicId);
    }

}

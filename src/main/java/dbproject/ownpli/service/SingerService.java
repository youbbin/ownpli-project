package dbproject.ownpli.service;

import dbproject.ownpli.domain.SingerEntity;
import dbproject.ownpli.repository.SingerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SingerService {

    private final SingerRepository singerRepository;

    /**
     * 가수 전체 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<SingerEntity> findSingers() {
        return singerRepository.findAll();
    }

    /**
     * 가수 단일 조회
     * @param singerId
     * @return
     */
    @Transactional(readOnly = true)
    public SingerEntity findBysingerId(String singerId) {
        return singerRepository.findById(singerId).get();
    }

    /**
     * 가수 이름으로 가수 정보 조회
     * @param singerName
     * @return
     */
    public SingerEntity findBySingerName(String singerName) {
        return singerRepository.findBySingerName(singerName);
    }


}

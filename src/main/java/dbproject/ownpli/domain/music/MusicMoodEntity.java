package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

/**
 * 조인으로 연동하기
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "MUSICMOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicMoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicMoodId;

    private String musicId;

    private String moodNum;

}

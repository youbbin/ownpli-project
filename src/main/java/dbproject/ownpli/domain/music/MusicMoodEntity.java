package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

/**
 * 조인으로 연동하기
 */
@Data
@Builder
@AllArgsConstructor
@Entity(name = "music-mood")
@Table(name = "music-mood")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicMoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(name = "music id")
    private String musicId;

    @Column(name = "mood num")
    private String moodNum;

}

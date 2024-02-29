package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

/**
 * 조인으로 연동하기
 */
@Data
@Builder
@AllArgsConstructor
@Entity(name = "music_mood")
@Table(name = "music-mood")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicMoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicMoodId;

    @ManyToOne
    @JoinColumn(name = "music id", referencedColumnName = "music id")
    private MusicEntity musicEntity;

    @ManyToOne
    @JoinColumn(name = "mood num", referencedColumnName = "mood num")
    private MoodEntity moodEntity;

}

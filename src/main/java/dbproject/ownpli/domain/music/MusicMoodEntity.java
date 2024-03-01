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
@Table(name = "music_mood")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicMoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicMoodId;

    @ManyToOne
    @JoinColumn(name = "music_id", referencedColumnName = "music_id")
    private MusicEntity musicEntity;

    @ManyToOne
    @JoinColumn(name = "mood_num", referencedColumnName = "mood_num")
    private MoodEntity moodEntity;

}

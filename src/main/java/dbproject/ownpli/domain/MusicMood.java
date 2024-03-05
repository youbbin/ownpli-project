package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 조인으로 연동하기
 */
@Getter
@Entity
@Table(name = "music_mood")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicMood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicMoodId;

    @ManyToOne
    @JoinColumn(name = "music_id", referencedColumnName = "music_id")
    private Music music;

    @ManyToOne
    @JoinColumn(name = "mood_num", referencedColumnName = "mood_num")
    private Mood mood;

}

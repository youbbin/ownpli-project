package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "MUSICMOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicMoodEntity {

    @Id
    private Long musicMoodId;

    @OneToOne(fetch = FetchType.LAZY)
    private MusicEntity musicId;

    @ManyToOne(fetch = FetchType.LAZY)      //다대일 단방향 매핑
    @JoinColumn(name="moodId")
    private MoodEntity moodNum;

}

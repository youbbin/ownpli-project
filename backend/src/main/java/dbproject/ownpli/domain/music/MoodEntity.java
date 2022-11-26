package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "mood")
@Builder
@Table(name = "mood")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoodEntity {

    @Id
    @Column(name = "mood num", nullable = false)
    private Long moodNum;

    @Column(nullable = false, length = 20)
    private String mood;
}

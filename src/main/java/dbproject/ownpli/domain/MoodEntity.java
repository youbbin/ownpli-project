package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@Table(name = "mood")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoodEntity {

    @Id
    @Column(name = "mood_num", nullable = false)
    private Long moodNum;

    @Column(nullable = false, length = 20)
    private String mood;
}

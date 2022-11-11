package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "MOOD")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoodEntity {

    @Id
    @Column(nullable = false)
    private Long moodId;

    @Column(nullable = false, length = 20)
    private String moodName;
}

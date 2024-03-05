package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "mood")
public class Mood {

    @Id
    @Column(name = "mood_num", nullable = false)
    private Long moodNum;

    @Column(nullable = false, length = 20)
    private String mood;
}

package dbproject.ownpli.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "Singer")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gId;

    @Column(nullable = false, length = 50)
    private String GenreID;

    @Column(nullable = false, length = 50)
    private String GenreName;
}

package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@Table(name = "genre")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenreEntity {

    @Id
    @Column(name = "genre_num", nullable = false)
    private Long genreNum;

    @Column(length = 20)
    private String genre;
}

package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "GENRE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenreEntity {

    @Id
    @Column(nullable = false)
    private Long genreId;

    @Column(length = 20)
    private String genreName;
}

package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "genre")
@Builder
@Table(name = "genre")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenreEntity {

    @Id
    @Column(name = "genre num", nullable = false)
    private Long genreNum;

    @Column(length = 20)
    private String genre;
}

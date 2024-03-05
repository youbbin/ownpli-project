package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @Column(name = "genre_num", nullable = false)
    private Long genreNum;

    @Column(length = 20)
    private String genre;
}

package dbproject.ownpli.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "playlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicEntity {

    @Id
    private String musicId;

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column(nullable = false)
    private int genreNum;

    @Column(nullable = false, length = 50)
    private String photo;

    @Column(nullable = false)
    private Date date;

}

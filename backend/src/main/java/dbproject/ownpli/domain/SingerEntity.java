package dbproject.ownpli.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "SINGER")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingerEntity {

    @Id
    @Column(name = "SINGER_ID", nullable = false, length = 50)
    private String singerId;

    @Column(nullable = false, length = 50)
    private String singerName;

    @Column(nullable = false, length = 100)
    private String singerProfile;
}

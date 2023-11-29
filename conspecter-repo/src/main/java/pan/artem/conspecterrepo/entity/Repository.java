package pan.artem.conspecterrepo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "repo")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String author;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "repository",
            cascade = CascadeType.REMOVE
    )
    private List<Conspect> conspects;
}

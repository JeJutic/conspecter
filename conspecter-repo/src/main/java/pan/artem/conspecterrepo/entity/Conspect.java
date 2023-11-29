package pan.artem.conspecterrepo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "conspect")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Conspect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    Repository repository;

    @OneToMany(mappedBy = "conspect", cascade = CascadeType.REMOVE)
    private List<Task> tasks;
}

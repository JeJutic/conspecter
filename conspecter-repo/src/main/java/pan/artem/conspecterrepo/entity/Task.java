package pan.artem.conspecterrepo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "conspect_id")
    Conspect conspect;

    @OneToMany(mappedBy = "currentTask")
    List<User> currentUsers;

    @ManyToMany(mappedBy = "tasksCompleted")
    Set<User> usersSolved;
}

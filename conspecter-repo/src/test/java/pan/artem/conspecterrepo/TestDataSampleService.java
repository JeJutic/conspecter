package pan.artem.conspecterrepo;

import org.springframework.stereotype.Service;
import pan.artem.conspecterrepo.entity.Conspect;
import pan.artem.conspecterrepo.entity.Repository;
import pan.artem.conspecterrepo.entity.Task;
import pan.artem.conspecterrepo.entity.User;
import pan.artem.conspecterrepo.repository.ConspectRepository;
import pan.artem.conspecterrepo.repository.RepoRepository;
import pan.artem.conspecterrepo.repository.TaskRepository;
import pan.artem.conspecterrepo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class TestDataSampleService {

    private final RepoRepository repoRepository;
    private final ConspectRepository conspectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TestDataSampleService(RepoRepository repoRepository,
                                 ConspectRepository conspectRepository,
                                 TaskRepository taskRepository,
                                 UserRepository userRepository) {
        this.repoRepository = repoRepository;
        this.conspectRepository = conspectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private void initialData() {
        var repo = new Repository();
        repo.setName("repo-name");
        repo.setAuthor("repo-author");
        repo = repoRepository.save(repo);

        var conspect = new Conspect();
        conspect.setName("conspect-name");
        conspect.setRepository(repo);
        conspect = conspectRepository.save(conspect);

        var task1 = new Task();
        task1.setText("task1-text");
        task1.setAnswer("task1 answer");
        task1.setConspect(conspect);
        taskRepository.save(task1);

        var task2 = new Task();
        task2.setText("task2-text");
        task2.setAnswer("task2 answer");
        task2.setConspect(conspect);
        taskRepository.save(task2);

        var user = new User();
        user.setName("user");
        Set<Task> tasksCompleted = new HashSet<>();
        tasksCompleted.add(task1);
        user.setTasksCompleted(tasksCompleted);
        userRepository.save(user);
    }

    public void setUp() {
        userRepository.deleteAll();
        repoRepository.deleteAll();

        initialData();
    }
}

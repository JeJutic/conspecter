package pan.artem.conspecterweb.service;

import pan.artem.conspecterweb.dto.Conspect;
import pan.artem.conspecterweb.dto.Repository;
import pan.artem.conspecterweb.dto.SolvedTask;
import pan.artem.conspecterweb.dto.Task;

import java.util.List;
import java.util.Optional;

public interface RepositoryServiceClient {

    List<Repository> getRepositories();

    List<Conspect> getConspects(String username, String repositoryName);

    Task getTask(String username, String repositoryName, String conspectName);

    SolvedTask sendSolution(String username, String solution);

    Optional<Task> getCurrentTask(String username);

    void uploadRepository(String author, String repoName, String url);
}

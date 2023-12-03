package pan.artem.conspecterweb.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pan.artem.conspecterweb.dto.Conspect;
import pan.artem.conspecterweb.dto.Repository;
import pan.artem.conspecterweb.dto.SolvedTask;
import pan.artem.conspecterweb.dto.Task;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RepositoryServiceClientImpl implements RepositoryServiceClient {

    private final RestTemplate restTemplate;

    public RepositoryServiceClientImpl(
            @Qualifier("repositoryServiceEndpoint") RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Repository> getRepositories() {
        return List.of(Objects.requireNonNull(restTemplate.getForObject(
                "/",
                Repository[].class
        )));
    }

    @Override
    public List<Conspect> getConspects(String username, String repositoryName) {
        return List.of(Objects.requireNonNull(restTemplate.getForObject(
                "/{repositoryName}?username={username}",
                Conspect[].class,
                repositoryName,
                username
        )));
    }

    @Override
    public Task getTask(String username, String repositoryName, String conspectName) {
        return restTemplate.getForObject(
                "/{repositoryName}/{conspectName}?username={username}",
                Task.class,
                repositoryName,
                conspectName,
                username
        );
    }

    @Override
    public SolvedTask sendSolution(String username, String solution) {
        return restTemplate.postForObject(
                "/?username={username}&solution={solution}",
                null,
                SolvedTask.class,
                username,
                solution
        );
    }

    @Override
    public Optional<Task> getCurrentTask(String username) {
        return Optional.ofNullable(restTemplate.getForObject(
                "/current?username={username}",
                Task.class,
                username
        ));
    }

    @Override
    public void uploadRepository(String author, String repoName, String url) {
        restTemplate.exchange(
                "/init/{author}/{repoName}?url={url}",
                HttpMethod.POST,
                null,
                Void.class,
                author,
                repoName,
                url
        );
    }
}

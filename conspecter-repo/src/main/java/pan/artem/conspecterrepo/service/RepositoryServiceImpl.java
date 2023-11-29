package pan.artem.conspecterrepo.service;

import dto.RepositoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pan.artem.conspecterrepo.repository.RepoRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class RepositoryServiceImpl implements RepositoryService {

    private final RepoRepository repoRepository;

    @Override
    public List<RepositoryDto> findAll() {
        return repoRepository.findAll().stream()
                .map(r -> new RepositoryDto(r.getAuthor(), r.getName()))
                .toList();
    }
}

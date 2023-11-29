package pan.artem.conspecterrepo.service;

import dto.ConspectDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pan.artem.conspecterrepo.repository.ConspectRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ConspectServiceImpl implements ConspectService {

    private final ConspectRepository conspectRepository;

    @Override
    public List<ConspectDto> findAllByRepoName(String username, String repoName) {
        return conspectRepository.findAllByRepoName(repoName).stream()
                .map(c -> new ConspectDto(
                        c.getName(),
                        conspectRepository.countTasks(c),
                        conspectRepository.countSolvedTasks(username, c)))
                .toList();
    }
}

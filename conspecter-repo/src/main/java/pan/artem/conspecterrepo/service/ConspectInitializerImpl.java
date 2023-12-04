package pan.artem.conspecterrepo.service;

import dto.inner.TaskDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pan.artem.conspecterrepo.domain.ConspectAnalyzer;
import pan.artem.conspecterrepo.domain.ConspectAnalyzerImpl;
import pan.artem.conspecterrepo.entity.Conspect;
import pan.artem.conspecterrepo.entity.Repository;
import pan.artem.conspecterrepo.entity.Task;
import pan.artem.conspecterrepo.properties.AppProperties;
import pan.artem.conspecterrepo.repository.ConspectRepository;
import pan.artem.conspecterrepo.repository.TaskRepository;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@AllArgsConstructor
@Service
public class ConspectInitializerImpl implements ConspectInitializer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TaskRepository taskRepository;
    private final ConspectRepository conspectRepository;
    private final AppProperties.ParsingProperties parsingProperties;

    private Task taskFromDto(TaskDto taskDto, Conspect conspect) {
        var task = new Task();
        task.setText(taskDto.text());
        task.setAnswer(taskDto.answer());
        task.setConspect(conspect);
        return task;
    }

    private Conspect findOrCreate(Repository repository, File path) {
        String conspectName = path.getPath().replace('/', '_');
        var optionalConspect = conspectRepository.findByRepoNameAndConspectName(
                repository.getName(), conspectName).stream().findAny();

        return optionalConspect.orElseGet(() -> {
            var conspect = new Conspect();
            conspect.setName(conspectName);
            conspect.setRepository(repository);
            conspect.setTasks(new ArrayList<>());
            return conspectRepository.save(conspect);
        });
    }

    @Transactional
    @Override
    public void initialize(Repository repository, File path) throws ParseException, IOException {
        logger.info("Initializing conspect from repo {} with path {}", repository, path);

        ConspectAnalyzer analyzer = new ConspectAnalyzerImpl(parsingProperties);
        var tasks = analyzer.analyze(path);

        var conspect = findOrCreate(repository, path);
        conspect.getTasks().clear();
        conspectRepository.save(conspect);
        tasks.forEach(dto ->
                taskRepository.save(taskFromDto(dto, conspect))
        );
    }
}

package pan.artem.conspecterrepo.domain;

import dto.inner.TaskDto;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ConspectAnalyzer {

    List<TaskDto> analyze(File path) throws IOException, ParseException;
}

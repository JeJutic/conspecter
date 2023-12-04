package pan.artem.conspecterrepo.domain;

import dto.inner.TaskDto;

import java.util.List;

public interface TaskMaker {

    List<TaskDto> makeTasks(String text, int taskCnt);
}

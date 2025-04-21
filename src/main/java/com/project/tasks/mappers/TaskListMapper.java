package com.project.tasks.mappers;

import com.project.tasks.domain.dto.TaskListDto;
import com.project.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto (TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);


}

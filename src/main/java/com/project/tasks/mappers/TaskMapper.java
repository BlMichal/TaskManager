package com.project.tasks.mappers;

import com.project.tasks.domain.dto.TaskDto;
import com.project.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}

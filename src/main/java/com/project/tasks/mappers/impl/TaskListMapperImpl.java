package com.project.tasks.mappers.impl;

import com.project.tasks.domain.dto.TaskListDto;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.domain.entities.TaskList;
import com.project.tasks.domain.entities.TaskStatus;
import com.project.tasks.mappers.TaskListMapper;
import com.project.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {

        List<Task> tasks = taskListDto.tasks() != null
                ? taskListDto.tasks().stream()
                .map(taskMapper::fromDto)
                .collect(Collectors.toList())
                : null;

        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                tasks,
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks ->
                                tasks.stream().map(taskMapper::toDto).toList()
                        ).orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks)
    {
        if(null == tasks){return null;}

       long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED == task.getStatus()).count();

        return (double) closedTaskCount / tasks.size();
    }
}

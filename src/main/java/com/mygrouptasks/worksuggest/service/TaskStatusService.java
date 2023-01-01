package com.mygrouptasks.worksuggest.service;


import com.mygrouptasks.worksuggest.model.TaskStatus;
import com.mygrouptasks.worksuggest.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public List<TaskStatus> getAllTaskStatusesForTask(int taskId) {
        List<TaskStatus> taskStatuses = taskStatusRepository.getAllTaskStatusForTaskSortDesc(taskId);
        return taskStatuses;
    }

    public TaskStatus getMostRecentTasksStatusForTask(int taskId) {
        List<TaskStatus> taskStatuses = taskStatusRepository.getAllTaskStatusForTaskSortDesc(taskId);
        if (taskStatuses.size() == 0) {
            TaskStatus EmptyTaskStatus = new TaskStatus();
            EmptyTaskStatus.setStatus("No status written");
            return EmptyTaskStatus;
        }
        else { return taskStatuses.get(0); }
    }

    public void updateTaskStatus(
            String status, String comments, String startDateTime,
            String endDateTime, String duration, int taskStatusId
    ) {
        taskStatusRepository.updateTaskStatus(status,
                comments,
                startDateTime, endDateTime,
                duration, taskStatusId);
    }

    public List<TaskStatus> getAllTaskStatuses() {
        List<TaskStatus> taskStatuses = new ArrayList<>();
        taskStatusRepository.findAll().forEach(taskStatuses::add);
        return taskStatuses;
    }

    public void addTaskStatus(TaskStatus taskStatus){
        taskStatusRepository.save(taskStatus);
        System.out.println("- added taskstatus: " + taskStatus.toString());
    }

    public TaskStatus getTaskStatus(int id) {
        return taskStatusRepository.findById(id);
    }
}

package com.mygrouptasks.worksuggest.service;

import com.mygrouptasks.worksuggest.model.Task;
import com.mygrouptasks.worksuggest.model.TaskStatus;
import com.mygrouptasks.worksuggest.repository.TaskRepository;
import com.mygrouptasks.worksuggest.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public void removeTask(int task_id) {
        Task task = this.getTask(task_id);
        taskRepository.delete(task);
        //taskRepository.removeTask(task_id);
        System.out.println("- removed task with id " + task_id);
    }

    public Task getTaskForPerson(int person_id, int task_id) {
        Task myTask = taskRepository.findByIdAndPersonId(person_id, task_id);
        return myTask;
    }

    public List<Task> getAllTasksMostRecentFirst() {
        List<Task> gettAllTasksDesc = new ArrayList<>();
        return taskRepository.getAllDesc();
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    public void addTask(Task task){
        taskRepository.save(task);
        System.out.println("- added task: " + task.toString());
    }

    public Task getTask(int id) {
        return taskRepository.findById(id);
    }

    //this might return several tasks if task titles in a list are similar.
    //suggestion: get by a random stamp or get by id
    public int getTaskIdGivenTitleAndWorkListId(String taskTitle,
                                                 int workListId) {
        int taskId = taskRepository.findIdByTitleAndWorkListId(taskTitle, workListId);
        return taskId;
    }

    public Task toEntity (int person_id,
                          String title,
                          String description,
                          String resource_description,
                          int worklist_id) {
        Task newTask = new Task();
        newTask.setPerson_id(person_id);
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setResource_description(resource_description);
        newTask.setWorklist_id(worklist_id);
        return newTask;
    }
}

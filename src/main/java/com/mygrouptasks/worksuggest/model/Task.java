package com.mygrouptasks.worksuggest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int task_id;
    @Column
    private int worklist_id;
    @Column
    private int person_id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String resource_description;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<TaskStatus> taskStatuses;

    @ManyToMany(mappedBy = "tasks", cascade = {CascadeType.ALL})
    private Set<Person> persons = new HashSet<Person>();

    public Task(int worklist_id,
                int person_id,
                String title,
                String description,
                String resource_description) {
        this.worklist_id = worklist_id;
        this.person_id = person_id;
        this.title = title;
        this.description = description;
        this.resource_description = resource_description;
    };

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public Set<TaskStatus> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(Set<TaskStatus> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getWorklist_id() {
        return worklist_id;
    }

    public void setWorklist_id(int worklist_id) {
        this.worklist_id = worklist_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource_description() {
        return resource_description;
    }

    public void setResource_description(String resource_description) {
        this.resource_description = resource_description;
    }

    @Override
    public String toString() {
        String result = String.format(
                "Task[id=%d, title='%s']%n",
                task_id, title);
        if (taskStatuses != null) {
            for (TaskStatus ts : taskStatuses) {
                result += String.format(
                        "TaskStatus[id=%d,status='%s']%n",
                        ts.getTask_id(), ts.getStatus());
            }
        }
        return result;
    }
}
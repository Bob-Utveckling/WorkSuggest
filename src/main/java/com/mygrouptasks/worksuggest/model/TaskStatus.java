package com.mygrouptasks.worksuggest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="taskstatus")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int taskstatus_id;
    @ManyToOne(fetch = FetchType.LAZY /*, optional = false*/)
    @JoinColumn(name = "task_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Task task;

    private String status;
    private String comments;
    private String startdatetime;
    private String enddatetime;
    private String duration;

    public TaskStatus(String status,
               String comments,
               String startdatetime,
               String enddatetime,
               String duration,
               Task task) {
        this.status = status;
        this.comments = comments;
        this.startdatetime = startdatetime;
        this.enddatetime = enddatetime;
        this.duration = duration;
        this.task = task;
    }

    private int getTaskstatus_id()
    { return taskstatus_id; }

    private Task getTask(){
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getTask_id() {
        return taskstatus_id;
    }

    public void setTask_id(int task_id) {
        this.taskstatus_id = task_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(String startdatetime) {
        this.startdatetime = startdatetime;
    }

    public String getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(String enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "taskstatus_id: " + this.taskstatus_id + ", " +
                "status: " + this.getStatus() + ", " +
                "comments: " + this.getComments();
    }
}
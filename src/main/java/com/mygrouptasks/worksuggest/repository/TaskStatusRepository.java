package com.mygrouptasks.worksuggest.repository;


import com.mygrouptasks.worksuggest.model.Task;
import com.mygrouptasks.worksuggest.model.TaskStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = false)
@Repository
public interface TaskStatusRepository extends CrudRepository<TaskStatus,String> {

    @Modifying
    @Query(value = "UPDATE TaskStatus SET status = ?1, comments = ?2, startdatetime = ?3, enddatetime = ?4, duration=?5 WHERE taskstatus_id = ?6", nativeQuery = true)
    void updateTaskStatus(String status, String comments, String startdatetime, String enddatetime, String duration, int tsId);
    //@Query("UPDATE TaskStatus ts SET ts.status = ?1, ts.comments = ?2, ts.duration = ?3, ts.startdatetime = ?4, ts.enddatetime = ?5 WHERE ts.taskstatus_id = ?6")

    @Query(value="select ts from TaskStatus ts where ts.taskstatus_id =:id")
    TaskStatus findById(@Param("id") int id);

    @Query(value="select * from TaskStatus where task_id = ?1 order by taskstatus_id desc", nativeQuery = true)
    List<TaskStatus> getAllTaskStatusForTaskSortDesc(int task_id);

}
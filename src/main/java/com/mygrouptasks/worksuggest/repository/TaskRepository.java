package com.mygrouptasks.worksuggest.repository;

import com.mygrouptasks.worksuggest.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional(readOnly = false)
@Repository
public interface TaskRepository extends CrudRepository<Task,String> {


    @Query(value="select * from task order by task_id desc", nativeQuery = true)
    List<Task> getAllDesc();

    @Query(value="select t from Task t where t.task_id =:task_id AND t.person_id =:person_id")
    Task findByIdAndPersonId(@Param("person_id") int person_id,
                             @Param("task_id") int task_id);

    @Query(value="select t from Task t where t.task_id =:id")
    Task findById(@Param("id") int id);

    @Query(value="select t.task_id from Task t where t.title =:title AND t.worklist_id =:workListId")
    int findIdByTitleAndWorkListId(@Param("title") String title,
                                  @Param("workListId") int workListId);

}
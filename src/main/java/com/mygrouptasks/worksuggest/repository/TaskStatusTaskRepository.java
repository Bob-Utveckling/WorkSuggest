package com.mygrouptasks.worksuggest.repository;

import com.mygrouptasks.worksuggest.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusTaskRepository extends CrudRepository<Task,String> {

}

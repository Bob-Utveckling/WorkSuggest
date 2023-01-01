package com.mygrouptasks.worksuggest.repository;

import com.mygrouptasks.worksuggest.model.Person;
import com.mygrouptasks.worksuggest.model.WorkSuggest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
@Repository
public interface WorkSuggestRepository extends CrudRepository<WorkSuggest, String> {

    @Query(value="select ws from WorkSuggest ws where ws.worksuggest_id =:id")
    WorkSuggest findById(@Param("id") int id);

    @Modifying
    @Query("UPDATE WorkSuggest ws SET ws.generalstatus = ?1 WHERE ws.worksuggest_id = ?2")
    void updateStatusForWorkSuggestList(String generalsstatus, int workSuggestListId);

}
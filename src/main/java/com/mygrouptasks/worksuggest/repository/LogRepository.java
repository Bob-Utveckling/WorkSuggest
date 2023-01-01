package com.mygrouptasks.worksuggest.repository;

import com.mygrouptasks.worksuggest.model.Admin;
import com.mygrouptasks.worksuggest.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
@Repository
public interface LogRepository extends CrudRepository<Log,String> {

}

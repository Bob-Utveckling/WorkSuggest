package com.mygrouptasks.worksuggest.repository;

import com.mygrouptasks.worksuggest.model.Admin;
import com.mygrouptasks.worksuggest.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface AdminRepository extends CrudRepository<Admin,String> {

    @Query(value="select a from Admin a where a.admin_id =:id")
    Admin findById(@Param("id") int id);
}

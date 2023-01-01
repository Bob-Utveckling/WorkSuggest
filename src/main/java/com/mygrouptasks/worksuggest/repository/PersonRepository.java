package com.mygrouptasks.worksuggest.repository;

import com.mygrouptasks.worksuggest.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional(readOnly = true)
@Repository
public interface PersonRepository extends CrudRepository<Person,String> {

    @Query(value="select p from Person p where p.person_id =:id")
    Person findById(@Param("id") int id);
}

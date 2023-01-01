package com.mygrouptasks.worksuggest.service;

import com.mygrouptasks.worksuggest.model.Person;
//import com.mygrouptasks.worksuggest.repository.PersonRepository;
import com.mygrouptasks.worksuggest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        personRepository.findAll().forEach(persons::add);
        return persons;
    }

    public void addPerson(Person person){
        personRepository.save(person);
    }

    public Person getPerson(int id) {
        return personRepository.findById(id);
    }
}

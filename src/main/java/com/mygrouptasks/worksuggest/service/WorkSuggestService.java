package com.mygrouptasks.worksuggest.service;

import com.mygrouptasks.worksuggest.model.Person;
import com.mygrouptasks.worksuggest.model.Task;
import com.mygrouptasks.worksuggest.model.TaskStatus;
import com.mygrouptasks.worksuggest.model.WorkSuggest;
import com.mygrouptasks.worksuggest.repository.WorkSuggestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkSuggestService {

    @Autowired
    private WorkSuggestRepository workSuggestRepository;

    public void removeWorkSuggest(int worksuggest_id) {
        try {
            WorkSuggest workSuggest = this.getById(worksuggest_id);
            workSuggestRepository.delete(workSuggest);
            System.out.println("- removed worksuggest with id " + worksuggest_id);
        } catch (Exception e) {
            System.out.println("- requested to remove a worksuggest list from an empty list");
        }

    }

    public void addWorkSuggest(WorkSuggest workSuggest){
        workSuggestRepository.save(workSuggest);
        System.out.println("- added work suggest: " + workSuggest.toString());
    }

    public void updateStatus(String generalstatus, int workSuggestListId) {
        workSuggestRepository.updateStatusForWorkSuggestList(generalstatus, workSuggestListId);
    }

    public List<WorkSuggest> getAllWorkSuggests() {
        List<WorkSuggest> workSuggest = new ArrayList<>();
        workSuggestRepository.findAll().forEach(workSuggest::add);
        return workSuggest;
    }

    public WorkSuggest getById(int id) {
        WorkSuggest workSuggest = workSuggestRepository.findById(id);
        return workSuggest;
    }

    public WorkSuggest toDefaultEntity(WorkSuggest workSuggest, int person_id) {
        WorkSuggest editedWorkSuggest = workSuggest;
        editedWorkSuggest.setTitle(workSuggest.getTitle());
        editedWorkSuggest.setDescription(workSuggest.getDescription());
        editedWorkSuggest.setAdminid(1);
        editedWorkSuggest.setPersonid(person_id);
        editedWorkSuggest.setGeneralstatus("waiting for review");
        return editedWorkSuggest;
    }
}

package com.mygrouptasks.worksuggest.controller;

import com.mygrouptasks.worksuggest.component.PanelService;
import com.mygrouptasks.worksuggest.model.Person;
import com.mygrouptasks.worksuggest.model.Task;
import com.mygrouptasks.worksuggest.model.WorkSuggest;
import com.mygrouptasks.worksuggest.service.PersonService;
import com.mygrouptasks.worksuggest.service.TaskService;
import com.mygrouptasks.worksuggest.service.TaskStatusService;
import com.mygrouptasks.worksuggest.service.WorkSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import javax.validation.Valid;

@Controller
public class PersonWorksuggestController {

    @Autowired
    private PanelService panelService;

    private String message = "";

    @Autowired
    private WorkSuggestService workSuggestService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskStatusService taskStatusService;

    //JMS
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @PostMapping("/person/{person_id}/worksuggestlist/suggest/")
    public String suggestwork_post(Model model,
                                   @PathVariable int person_id,
                                   @Valid @ModelAttribute WorkSuggest workSuggest)
    {
        System.out.println("suggest work submitted...");
        WorkSuggest preparedWorkSuggest = workSuggestService.toDefaultEntity(workSuggest, person_id);
        System.out.println(preparedWorkSuggest.toString());
        workSuggestService.addWorkSuggest(preparedWorkSuggest);
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        model.addAttribute("baseURL", panelService.getBaseURL());
        this.jmsMessagingTemplate.convertAndSend(this.queue, "Work Suggestion submitted by user");
        return panelService.returnPersonPanel("Work Suggestion has been submitted for review", person_id, model);
    }


    @GetMapping("/person/{person_id}/worksuggestlist/suggest/")
    public String suggestwork(Model model,
        @PathVariable int person_id)
    {
        System.out.println("suggest work...");

        model.addAttribute("worksuggest", new WorkSuggest());

        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        model.addAttribute("baseURL", panelService.getBaseURL());

        return "worksuggestlist_add";
    }


    @GetMapping("/person/{person_id}/worksuggestlist/{worksuggest_id}/")
    public String update_task_status(Model model,
                                     @PathVariable int person_id,
                                     @PathVariable int worksuggest_id) {

        //TaskStatus taskStatus = taskStatusService.getTaskStatus(taskstatus_id);

        WorkSuggest workSuggest = workSuggestService.getById(worksuggest_id);
        System.out.println("- show work suggest list: " + worksuggest_id);
        System.out.println("- its title: " + workSuggest.getTitle());
        /*not working, instead setting constraint in the table:
        if (workSuggest.getAdminid() == NULL) { workSuggest.setAdminid(0); };
        if (workSuggest.getPersonid() == NULL) { workSuggest.setPersonid(0); };
        if (workSuggest.getGeneralstatus().isEmpty()) { workSuggest.setGeneralstatus(""); };
         */
        model.addAttribute("worksuggest", workSuggest); //should do with one
        return "worksuggestlist";
    }


}

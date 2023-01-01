package com.mygrouptasks.worksuggest.controller;

import com.mygrouptasks.worksuggest.component.PanelService;
import com.mygrouptasks.worksuggest.model.Person;
import com.mygrouptasks.worksuggest.model.Task;
import com.mygrouptasks.worksuggest.model.TaskStatus;
import com.mygrouptasks.worksuggest.model.WorkSuggest;
import com.mygrouptasks.worksuggest.repository.PersonRepository;
import com.mygrouptasks.worksuggest.service.PersonService;
import com.mygrouptasks.worksuggest.service.TaskService;
import com.mygrouptasks.worksuggest.service.TaskStatusService;
import com.mygrouptasks.worksuggest.service.WorkSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import javax.validation.Valid;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.*;

@Controller
public class PersonTaskController {

    @Autowired
    private PanelService panelService;

    private String message = "";


    @Autowired
    private WorkSuggestService workSuggestService;
    @Autowired
    private PersonRepository personRepository;
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

    @GetMapping("/person/{person_id}/task/{task_id}/remove/")
    public String remove_task(@PathVariable int task_id,
                              @PathVariable int person_id,
                              Model model) {
        taskService.removeTask(task_id);
        this.jmsMessagingTemplate.convertAndSend(this.queue,"User deleted task with id " + task_id);
        return panelService.returnPersonPanel("Task with id " + task_id + " has been removed", person_id, model);
    }

    @PostMapping("/person/{person_id}/task/{task_id}/addPerson/")
    public String post_add_person_to_task(@PathVariable int person_id,
                                          @PathVariable int task_id,
                                          @RequestParam int personToAdd_id,
                                          Model model) {

        System.out.println("- person to add id received: " + personToAdd_id);
        Person newPerson = personService.getPerson(personToAdd_id);
        System.out.println("- new person to add. Person's name: " + newPerson.getName());
        Task task = taskService.getTask(task_id);
        newPerson.getTasks().add(task);
        personRepository.save(newPerson);

        this.jmsMessagingTemplate.convertAndSend(this.queue, "User added person " + newPerson.getUsername() + " to task with id " + task_id);
        message = "Task: \"" + task.getTitle() + "\" has been added for the given person: " + newPerson.getName();
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        return panelService.returnPersonPanel(message, person_id, model);
    }

    @GetMapping("/person/{person_id}/task/{task_id}/addPerson/")
    public String get_add_person_to_task(@PathVariable int person_id,
                                     @PathVariable int task_id,
                                     Model model) {
        System.out.println("add a person to task: " + task_id + "");
        //improve: right now not limiting persons availability to specific persons for this person

        Task task = taskService.getTask(task_id);
        model.addAttribute("taskTitle", task.getTitle());

        //load all persons
        List<Person> personList = personService.getAllPersons();
        model.addAttribute("persons", personList);
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        return "add_person_to_task";
    }

    @GetMapping("/person/{person_id}/task/{task_id}/allTaskStatuses")
    public String see_all_task_statuses(@PathVariable int task_id,
                                        @PathVariable int person_id,
                                        Model model) {
        List<TaskStatus> taskStatusList = taskStatusService.getAllTaskStatusesForTask(task_id);
        String taskTitle = taskService.getTask(task_id).getTitle();
        model.addAttribute("baseURL", panelService.getBaseURL());
        model.addAttribute("person_id", person_id);
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        model.addAttribute("task_id", task_id);
        model.addAttribute("taskTitle",taskTitle);
        model.addAttribute("taskstatuses",taskStatusList);
        return "see_all_statuses_for_task";
    }


    @PostMapping("/person/{person_id}/task/{task_id}/updateTaskStatus/{taskstatus_id}/")
    public String post_add_task_status(@Valid @ModelAttribute TaskStatus taskStatus,
                                       @PathVariable int person_id,
                                       @PathVariable int taskstatus_id,
                                       Model model) {
        System.out.println("- now updating...");
        taskStatusService.updateTaskStatus(taskStatus.getStatus(),
                taskStatus.getComments(),
                taskStatus.getStartdatetime(),
                taskStatus.getEnddatetime(),
                taskStatus.getDuration(),
                taskstatus_id);
        System.out.println("- updating done.");
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);

        this.jmsMessagingTemplate.convertAndSend(this.queue, "User updated Task Status " + taskstatus_id);
        message = "The Task Status has been updated";
        return panelService.returnPersonPanel(message, person_id, model);
    }

    @GetMapping("/person/{person_id}/task/{task_id}/updateTaskStatus/{taskstatus_id}/")
    public String update_task_status(Model model,
                                     @PathVariable int person_id,
                                     @PathVariable int task_id,
                                     @PathVariable int taskstatus_id) {
        //improve: perhaps not need person_id and task_id but keeping for the moment
        TaskStatus taskStatus = taskStatusService.getTaskStatus(taskstatus_id);
        model.addAttribute("taskstatus",taskStatus);
        System.out.println("- updating task status: " + taskStatus.toString());
        model.addAttribute("baseURL", panelService.getBaseURL());
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        return "edit_task_status";
    }

    @PostMapping("/person/{person_id}/task/{task_id}/addTaskStatus/")
    public String post_add_task_status(@Valid @ModelAttribute TaskStatus taskStatus,
                                       Model model,
                                       @PathVariable int person_id,
                                       @PathVariable int task_id,
                                       BindingResult bindingResult) {
        //public String post_suggest_a_task(Model model, @PathVariable int person_id, BindingResult bindingResult) {
        /*if (bindingResult.hasErrors()) {
            System.out.println("suggest_a_task has error");
            return "suggest_a_task";
        }
         */
        System.out.println("person_id: " + person_id);
        System.out.println("task_id: " + task_id);
        Task task = taskService.getTask(task_id);
        taskStatus.setTask(task);

        System.out.println("status: " + taskStatus.getStatus());
        System.out.println("comment: " + taskStatus.getComments());
        System.out.println("duration: " + taskStatus.getDuration());
        System.out.println("start date time: " + taskStatus.getStartdatetime());
        System.out.println("end date time: " + taskStatus.getEnddatetime());

        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);

        taskStatusService.addTaskStatus(taskStatus);
        model.addAttribute("baseURL", panelService.getBaseURL());

        this.jmsMessagingTemplate.convertAndSend(this.queue, "User added Task Status for task " + task_id );
        message = "The Task Status has been added";

        return panelService.returnPersonPanel(message, person_id, model);
    }

    @GetMapping("/person/{person_id}/task/{task_id}/addTaskStatus/")
    public String add_task_status(Model model, @PathVariable int person_id,
                                  @PathVariable int task_id) {
        model.addAttribute("taskstatus", new TaskStatus());
        model.addAttribute("person_id",person_id);
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        model.addAttribute("task_id", task_id);
        Task task = taskService.getTask(task_id);
        model.addAttribute("taskTitle", task.getTitle());
        model.addAttribute("baseURL", panelService.getBaseURL());
        return "add_task_status";

    }

    @GetMapping("/person/{person_id}/task/{task_id}")
    public String show_details_of_task(Model model, @PathVariable int person_id,
                                       @PathVariable int task_id) {
        try {
            Task task = taskService.getTaskForPerson(person_id, task_id);
            System.out.println("- task id for the task: " + task.getTask_id());
            System.out.println("- task title for the task: " + task.getTitle());
            model.addAttribute("baseURL", panelService.getBaseURL());
            return "blank";
        } catch (NullPointerException npe) {
            System.out.println("- task does not exist");
            model.addAttribute("baseURL", panelService.getBaseURL());
            return "blank";
        }
    }

    @PostMapping("/person/{person_id}/add_a_task/")
    public String post_suggest_a_task(@Valid @ModelAttribute Task task,
                                      Model model,
                                      @PathVariable int person_id,
                                      @RequestParam("worksuggestlistid") int worksuggestlistid,
                                      BindingResult bindingResult) {
    //public String post_suggest_a_task(Model model, @PathVariable int person_id, BindingResult bindingResult) {
        /*if (bindingResult.hasErrors()) {
            System.out.println("suggest_a_task has error");
            return "suggest_a_task";
        }
         */

        taskService.addTask(
                taskService.toEntity(task.getPerson_id(),
                task.getTitle(), task.getDescription(), task.getResource_description(),
                worksuggestlistid));

        Person person = personService.getPerson(person_id); //improve: read more about optional at: https://www.tutorialspoint.com/java8/java8_optional_class.htm
        model.addAttribute("person", person);
        System.out.println("person with id: " + person_id + ", name: " + person.getUsername());

        this.jmsMessagingTemplate.convertAndSend(this.queue, "User added Task");
        message = "The suggested task has been submitted";

        return panelService.returnPersonPanel(message, person_id, model);
    }

    @GetMapping("/person/{person_id}/add_a_task/")
    public String suggest_a_task(Model model, @PathVariable int person_id)
    {

        this.jmsMessagingTemplate.convertAndSend(this.queue, "Suggesting a task is initiated.");
        System.out.println("Message has been put to queue by sender");

        /* select all worksuggestlists assosicated with this person.
        show all of them here
        as a list add it as an attribute
         */
        List<WorkSuggest> workSuggestList = workSuggestService.getAllWorkSuggests();
        model.addAttribute("worksuggests", workSuggestList);

        model.addAttribute("baseURL", panelService.getBaseURL());
        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        model.addAttribute("person_id",person_id);
        model.addAttribute("task", new Task());
        return "add_a_task";
    }

    @RequestMapping(value = "/register_as_person", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("baseURL", panelService.getBaseURL());
        model.addAttribute("person", new Person());
        return "registration"; }

    @RequestMapping(value="/register_as_person", method= RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute ("person") Person getPerson, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        this.jmsMessagingTemplate.convertAndSend(this.queue, "New User has registered" );
        personService.addPerson(getPerson);
        model.addAttribute("baseURL", panelService.getBaseURL());
        return "registered";
    }

    @GetMapping(value = "/person/{person_id}")
    public String getPersonById (Model model, @PathVariable int person_id) { //improve: personId can be long
        person_id = person_id;
        message = "Welcome to your control panel.";
        return panelService.returnPersonPanel(message, person_id, model);

    }

}

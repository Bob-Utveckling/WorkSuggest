package com.mygrouptasks.worksuggest.component;

import com.mygrouptasks.worksuggest.model.*;
import com.mygrouptasks.worksuggest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Component
public class PanelService {

    @Autowired
    private PersonService personService;
    @Autowired
    private WorkSuggestService workSuggestService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private AdminService adminService;

    public String getBaseURL() {
      return "http://localhost:8080/worksuggest";
    }

    public String getMessage() {
        return "Welcome to the Work Suggestion Application";
    }


    /** panel that shows administrative works */
    public String returnAdminPanel(String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("baseURL", this.getBaseURL());
        Admin admin = adminService.getAdmin(1);
        model.addAttribute("admin", admin);
        List<WorkSuggest> workSuggestList = workSuggestService.getAllWorkSuggests();
        try {
            System.out.println("- title for first worksugest: " + workSuggestList.get(0).getTitle());
        } catch (Exception e) {
            workSuggestList.add(new WorkSuggest());
        }
        model.addAttribute("worksuggests", workSuggestList);
        model.addAttribute("baseURL", this.getBaseURL());
        return "admin";
    }


    /** panel that shows all user details */
    public String returnPersonPanel(String message,
                                    int person_id,
                                    Model model) {
        model.addAttribute("message", message);

        Person person = personService.getPerson(person_id);
        model.addAttribute("person", person);
        model.addAttribute("baseURL", this.getBaseURL());


        List<WorkSuggest> workSuggestList = workSuggestService.getAllWorkSuggests(); //but find according to person id
        model.addAttribute("workSuggests", workSuggestList);

        List<Task> taskList = taskService.getAllTasksMostRecentFirst();
        model.addAttribute("tasks", taskList);

        List<String> ListOfMostRecentTaskStatuses = new ArrayList<String>();
        TaskStatus lastTaskStatusForTask;
        //get the most recent task status
        for (Task task : taskList) {
            lastTaskStatusForTask = taskStatusService.getMostRecentTasksStatusForTask(task.getTask_id());
            System.out.println("- status in last task status for task " + task.getTask_id() + " is: " + lastTaskStatusForTask.getStatus());
            ListOfMostRecentTaskStatuses.add(lastTaskStatusForTask.getStatus());
        }
        model.addAttribute("taskstatuses", ListOfMostRecentTaskStatuses);
        model.addAttribute("baseURL", this.getBaseURL());
        return "personpanel";
    }
}

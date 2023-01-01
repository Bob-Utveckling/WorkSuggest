package com.mygrouptasks.worksuggest.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.mygrouptasks.worksuggest.component.PanelService;
import com.mygrouptasks.worksuggest.model.Admin;
import com.mygrouptasks.worksuggest.model.WorkSuggest;
import com.mygrouptasks.worksuggest.service.AdminService;
import com.mygrouptasks.worksuggest.service.WorkSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jms.Queue;
import java.util.List;


@Controller
public class AdminController {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    AdminService adminService;

    @Autowired
    WorkSuggestService workSuggestService;

    @Autowired
    PanelService panelService;

    @Value("${welcome.message}")
    private String message;

    @Value("http://localhost:8080/worksuggest_war_exploded")
    private String baseURL;

    @GetMapping("/admin/worksuggestlist/{listId}/remove/")
    public String remove_worksuggestlist(@PathVariable int listId, Model model) {
        workSuggestService.removeWorkSuggest(listId);
        System.out.println("- removed work suggest list with id " + listId);
        this.jmsMessagingTemplate.convertAndSend(this.queue, "Admin removed work suggest list with id '" + listId + "'");
        message = "List with id '" + listId + "' is removed.";
        return panelService.returnAdminPanel(message, model);

    }

    @GetMapping("/admin/setListToReviewing/{listId}")
    public String setListToReviewing(@PathVariable int listId, Model model) {
        System.out.println("- setting to review -- list with id " + listId + "...");

        workSuggestService.updateStatus("reviewing", listId);
        this.jmsMessagingTemplate.convertAndSend(this.queue, "Admin set for review a WorkSuggest List: List with Id '" + listId + "' has been just confirmed by administrators.");
        message = "List with id '" + listId + "' is set for reviewing.";
        return panelService.returnAdminPanel(message, model);
    }



    @GetMapping("/admin/confirmList/{listId}")
    public String adminConfirmList(@PathVariable int listId, Model model) {
        System.out.println("- confirming list with id " + listId + "...");

        workSuggestService.updateStatus("confirmed", listId);

        this.jmsMessagingTemplate.convertAndSend(this.queue, "Admin confirmed WorkSuggest List: List with Id '" + listId + "' has been just confirmed by administrators.");
        message = "List with id '" + listId + "' is now confirmed.";
        System.out.println("- sent JMS message: List with Id '" + listId + "' has been just confirmed by administrators.");
        return panelService.returnAdminPanel(message, model);
    }


    @GetMapping("/admin")
    public String admin(Model model) {
        System.out.println("- getting base URL: " + baseURL);
        return panelService.returnAdminPanel("Welcome to Admin page for the Work Suggest Application", model);
    }

    @GetMapping("/admin/messages")
    public String adminMessages(Model model) {
        return "admin_messages";
    }



}

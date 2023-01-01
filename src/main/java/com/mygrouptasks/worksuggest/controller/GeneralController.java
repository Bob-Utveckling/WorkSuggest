package com.mygrouptasks.worksuggest.controller;

import com.mygrouptasks.worksuggest.component.PanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class GeneralController {



    @Autowired
    private PanelService panelService;



    //========================================================================================

    @GetMapping("/login")
    public String login()
    {
        System.out.println("login...");
        return "/login";
    }

    @RequestMapping("/")
    public String start(Model model) {
        model.addAttribute("message", panelService.getMessage());
        return "welcome"; //view
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("baseURL", panelService.getBaseURL());
        return "about";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}

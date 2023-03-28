package com.example.webservice_for_online_testing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/login")
    public String login_to_index() {
        return "login";
    }
    @GetMapping("/author")
    public String greeting_to_author() {
        return "/author";
    }

}

package com.example.webservice_for_online_testing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller that handles start pages, that all users have access to
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Controller
public class LoginController {

    /**
     * Mapping handles first page of webservice.
     * @return http name of greetings page
     * See src/main/resources/templates/greeting.html in templates.
     */
    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    /**
     * Mapping handles login page configured by WebSecurityConfig.
     * @see com.example.webservice_for_online_testing.config.WebSecurityConfig
     * @return http name of login page
     * See src/main/resources/templates/login.html in templates.
     */

    @GetMapping("/login")
    public String loginToIndex() {
        return "login";
    }

    /**
     * Mapping handles page about author that contains main information about creator of the project and links
     * @return http name of author page
     * See src/main/resources/templates/author.html in templates.
     */
    @GetMapping("/author")
    public String authorPage() {
        return "/author";
    }

}

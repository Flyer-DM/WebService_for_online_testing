package com.example.webservice_for_online_testing.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.web.servlet.error.ErrorController;

/**
 * Controller for handling server errors
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Controller
public class TestingServiceErrorController implements ErrorController {

    /**
     * @deprecated
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(TestingServiceErrorController.class);

    /**
     * Method handles errors 404, 403, 405 and 500 and return name of certain page according to the error that occurred.
     * @param request HttpServletRequest object contains data from html page like ids, names, etc.
     * @return http name of error page
     */
    @RequestMapping("/error")
        public String handleError(HttpServletRequest request) {

        String errorPage = null;

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorPage = "error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorPage = "error/403";
            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                errorPage = "error/405";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorPage = "error/500";
            }
        }
        return errorPage;
    }

    /**
     * Method used for html button to redirect for greeting page
     * @return http name of greeting page
     * See src/main/resources/templates/greeting.html in templates.
     */
    @RequestMapping("/greeting")
    public String getBack() {
        return "greeting";
    }
}

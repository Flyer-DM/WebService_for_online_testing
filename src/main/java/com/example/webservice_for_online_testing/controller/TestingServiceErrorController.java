package com.example.webservice_for_online_testing.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.web.servlet.error.ErrorController;
@Controller
public class TestingServiceErrorController implements ErrorController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestingServiceErrorController.class);

    // обработка всех ошибок
    @RequestMapping("/error")
        public String handleError(HttpServletRequest request, Model model) {

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
    // кнопка возврата на страницу приветствия, в случае возникновения ошибки
    @RequestMapping("/greeting")
    public String getBack() {
        return "greeting";
    }
}

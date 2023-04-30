package com.example.webservice_for_online_testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main class runs Web service.
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@SpringBootApplication
public class WebServiceForOnlineTestingApplication extends SpringBootServletInitializer {

    /**
     * Main method of the program starts web-service.
     * @param args handles all arguments main method is assuming
     */
    public static void main(String[] args) {
        SpringApplication.run(WebServiceForOnlineTestingApplication.class, args);
    }
}

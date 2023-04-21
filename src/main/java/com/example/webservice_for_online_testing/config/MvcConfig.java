package com.example.webservice_for_online_testing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Used for static pages without server handling.
 * "login" page is handled by WebSecurityConfig
 * and "greeting" page is first page after login with links to users` pages.
 * @see WebSecurityConfig
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * Configures automated controllers with pre-defined view name and status code.
     * @param registry registers view controller without need to create actual controller class.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("greeting");
        registry.addViewController("/index").setViewName("index");
    }
}

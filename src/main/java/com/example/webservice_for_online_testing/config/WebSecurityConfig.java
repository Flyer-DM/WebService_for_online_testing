package com.example.webservice_for_online_testing.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Main class that configures all access permissions for different pages for two types of users.
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /** Value for student`s login hided from user in application.properties */
    @Value("${studentLogin}")
    private String studentLogin;

    /** Value for student`s password hided from user in application.properties */
    @Value("${studentPassword}")
    private String studentPassword;

    /** Value for teachers`s login hided from user in application.properties */
    @Value("${teacherLogin}")
    private String teacherLogin;

    /** Value for teachers`s password hided from user in application.properties */
    @Value("${teacherPassword}")
    private String teacherPassword;

    /**
     * @see MySimpleUrlAuthenticationSuccessHandler
     * @return configuring behaviour after successful user authentication
     */
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    /**
     * Method divides access permission for TEACHER role and USER role
     * and provides all users for specific pages.
     * @param http allows to configure web security for specific http requests
     * @return SecurityFilterChain a filter chain which is capable of being matched against an HttpServletRequest
     * in order to decide whether it applies to that request
     * @throws Exception any exception that can possibly raise
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers( "/css/**", "/js/**", "/", "/index").permitAll()
                .requestMatchers("/index_teacher", "/new_test", "/edit/*", "/edit_questions/*", "/show_results", "/histogram").hasRole("TEACHER")
                .requestMatchers("/index_student", "/student_testing/*", "/preresult", "/my_results").hasRole("STUDENT")
                .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/")
                        .loginProcessingUrl("/")
                        .successHandler(myAuthenticationSuccessHandler())
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    /**
     * Method saves in memory types of users, their logins and passwords.
     * @return UserDetailsService service that saves default names and passwords for two types of users
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(teacherLogin)
                .password(teacherPassword)
                .roles("TEACHER")
                .build();
        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username(studentLogin)
                .password(studentPassword)
                .roles("STUDENT")
                .build();
        return new InMemoryUserDetailsManager(user, user2);
    }
}

package com.example.webservice_for_online_testing.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Main class that configures all access permissions for different pages for two types of users
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    /**
     * Method divides access permission for TEACHER role and USER role
     * and provides all users for specific pages.
     * @param http allows configuring web based security for specific http requests.
     * @return SecurityFilterChain a filter chain which is capable of being matched against an HttpServletRequest
     * in order to decide whether it applies to that request.
     * @throws Exception any exception that can possibly raise.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/greeting", "/author", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/index", "/new_test", "/edit_test/*", "edit_questions/*", "/tests_results", "/histogram").hasRole("TEACHER")
                        .requestMatchers("/index_student", "/student_testing/*", "/preresult", "/my_results").hasRole("STUDENT")
                .anyRequest()
                .authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    /**
     * Method save in memory types of users, their names and passwords.
     * @return UserDetailsService service that saves default names and passwords for two types of users.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("1")
                .password("1")
                .roles("TEACHER") // роль преподавателя
                .build();
        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("2")
                .password("2")
                .roles("STUDENT")  // роль студента
                .build();
        return new InMemoryUserDetailsManager(user, user2);
    }
}

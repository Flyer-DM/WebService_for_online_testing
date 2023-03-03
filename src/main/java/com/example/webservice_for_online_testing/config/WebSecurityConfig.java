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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/greeting").permitAll()
                        .requestMatchers("/index", "/new_test", "/edit_test/*", "edit_questions/*").hasRole("TEACHER")
                        .requestMatchers("/index_student", "/student_testing/*").hasRole("STUDENT")
                .anyRequest()
                .authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

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

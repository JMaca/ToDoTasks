package org.example.ToDoTasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // tells Spring this will create and manage beans.
public class SecurityConfig {

    @Bean // object - class instance.
    public UserDetailsService users() { // retrieve user info for authentication.
        UserDetails user = User.builder()
                .username("myuser")
                .password("{noop}mypassword") // noop uses plain text (not secure, only for testing purposes).
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user); // Stores user in memory.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Spring securtiy handling HTTP requests.
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF protection is for browser-based sessions ( would block POST/GET requests).
                .authorizeHttpRequests(auth -> auth // Requires authentication on all requests.
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Basic authentication in the Authorization header
        return http.build(); // build and return bean
    }
}

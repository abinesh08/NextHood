package com.nexthood.notification_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("authUser")
                .password("{noop}password")
                .roles("AUTHORITY")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

package com.expense.config;

import com.expense.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> userRepository
                .findByEmail(username)

                .map(user ->

                        org.springframework.security
                                .core.userdetails.User

                                .builder()

                                .username(user.getEmail())

                                .password(user.getPassword())

                                .roles("USER")

                                .build()
                )

                .orElseThrow(() ->

                        new UsernameNotFoundException(
                                "User not found"
                        ));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/signup",
                                "/error",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl(
                                "/expenses",
                                true
                        )

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }
}
package com.example.leaderboardsystem.config;

import com.example.leaderboardsystem.service.CustomUserDetailsService;
import com.example.leaderboardsystem.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig (UserService userService) {
        this.userService = userService;
    }

    @Bean
    public UserDetailsService userDetailsService () {
        return new CustomUserDetailsService (this.userService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(DaoAuthenticationProvider authProvider) {
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .csrf (csrf -> csrf.disable ())
                .authorizeHttpRequests (auth -> auth
                        .requestMatchers ("/register", "/", "/login").permitAll ()
                        .anyRequest ().authenticated ()
                )
                .formLogin (form -> form
                        .loginPage ("/login")
                        .defaultSuccessUrl ("/leaderboard", true)
                        .failureUrl ("/login?error=true")
                        .permitAll ()
                )
                .logout (logout -> logout.permitAll ())
                .build ();
    }
}
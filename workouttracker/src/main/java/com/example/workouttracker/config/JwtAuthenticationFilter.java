package com.example.workouttracker.config;

import com.example.workouttracker.security.CustomUserDetailsService;
import com.example.workouttracker.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader ("Authorization");

        if (authHeader != null && authHeader.startsWith ("Bearer ")) {
            String jwt = authHeader.substring (7);

            if (this.jwtUtils.validateJwtToken (jwt)) {
                String username = this.jwtUtils.getUserNameFromJwtToken (jwt);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername (username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken (userDetails, null, userDetails.getAuthorities ());
                SecurityContextHolder.getContext ().setAuthentication (authentication);
            }
        }

        filterChain.doFilter (request, response);
    }
}
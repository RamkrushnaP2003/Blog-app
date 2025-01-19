package com.project.blogapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;

    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/users/login").permitAll()  // Allow login route for all users
                    .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")  // Allow access to /api/users/** for both USER and ADMIN
                    .requestMatchers("/api/users/all-users").hasRole("ADMIN")  // Only ADMIN can access this route
                    .anyRequest().authenticated()  // All other requests require authentication
            )
            .addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Add custom JWT filter

        return http.build();
    }

    // Custom filter for JWT Authentication
    private Filter jwtAuthenticationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                String authHeader = request.getHeader("Authorization");

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    String username = jwtUtil.extractUsername(token);
                    String role = jwtUtil.extractRole(token);  // Extract role from JWT

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        if (jwtUtil.isTokenValid(token, username)) {
                            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role); // Ensure role prefix
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    username, null, Collections.singletonList(authority)
                            );
                            authentication.setDetails(request);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }

                chain.doFilter(request, response);
            }
        };
    }
}

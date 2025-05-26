/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhom20.filters.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author nguyenho
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.nhom20.controllers",
    "com.nhom20.repositories",
    "com.nhom20.services"
})
public class SpringSecurityConfigs {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users", "/api/login", "/api/secure/profile", "/api/secure/user/update/**").permitAll()
                .requestMatchers("/api/secure/health-profiles",
                        "/api/secure/health-profile/**", "/api/secure/workout-plans", "/api/secure/workout-plan/add",
                        "/api/secure/exercises", "/api/secure/exercises/add",
                        "/api/secure/workout-plan-exercise/**", "/api/secure/workout-plan-exercise/add/**",
                        "/api/secure/trainers", "/api/secure/user-trainer/add", "/api/secure/user-trainers",
                        "/api/secure/health-journals", "/api/secure/health-journals/**",
                        "/api/secure/health-journal/add", "/api/secure/reminders",
                        "/api/secure/reminder/add", "/api/secure/reminders/**", "/api/secure/update-reminder/**",
                        "/api/secure/statistics").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/secure/request-user-trainers", "/api/secure/user-trainer/accept/**",
                        "/api/secure/user-trainer/reject/**", "/api/secure/accepted-user", "/api/secure/trainer-statistics",
                        "/api/secure/trainer-health-profile").hasAnyRole("TRAINER")
                .requestMatchers("/api/users/**", "/api/exercises/**", "/api/workout-plans/**", "/api/workout-plans-exercise/**","/api/user-trainer/**", "/api/health-journal/**", "/api/reminder/**", "/api/health-profiles/**").permitAll()
                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                .defaultAuthenticationEntryPointFor(
                        (req, res, ex) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"Unauthorized\"}");
                        },
                        new AntPathRequestMatcher("/api/**")
                )
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(c -> c.disable()).authorizeHttpRequests(requests
                -> requests.requestMatchers("/", "/home", "/health-profiles", "/health-profiles/**", "/health-profile/**",
                        "/users", "/users/**", "/user-add-view",
                        "/user-trainer", "/user-trainer/**", "/user-trainer-view",
                        "/workout-plans", "/workout-plan/**", "/workout-plans/**", "/workout-plans-view",
                        "/exercises", "/exercises/**", "/exercise-view",
                        "/workout-plans-exercise", "workout-plans-exercise/**", "/workout-plans-exercise-view",
                        "/health-journal", "/health-journal/**", "/health-journal-view",
                        "/reminders", "/reminder/**", "/reminder-view",
                        "/statistics").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll());
        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dnwyvuqej",
                        "api_key", "559324578186686",
                        "api_secret", "tjXbrfktUPN8lYMmE9SN-33QXjc",
                        "secure", true));
        return cloudinary;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000/")); // frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); // Nếu dùng cookie/session

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

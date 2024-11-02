package sda.spring.onlineticketmanagementsystem.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sda.spring.onlineticketmanagementsystem.middleware.JWTMiddleware;
import sda.spring.onlineticketmanagementsystem.service.AuthService;

@Configuration
@EnableWebSecurity
public class APIGuardConfig {

    private final AuthService authService;
    private final JWTMiddleware jwtMiddleware;

    public APIGuardConfig(AuthService authService, JWTMiddleware jwtMiddlware) {
        this.authService = authService;
        this.jwtMiddleware = jwtMiddlware;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login"
                        ).permitAll() // Public endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                );

        // Add JWT filter before UsernamePasswordAuthenticationFilter

        http.addFilterBefore(jwtMiddleware, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Set the user details service and password encoder
        authenticationManagerBuilder
                .userDetailsService(authService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}

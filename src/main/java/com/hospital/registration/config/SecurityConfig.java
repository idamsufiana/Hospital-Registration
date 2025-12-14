package com.hospital.registration.config;

import com.hospital.registration.dto.ApiErrorResponse;
import com.hospital.registration.exception.ApiErrorCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {

                            ApiErrorCode error = ApiErrorCode.UNAUTHORIZED;

                            response.setStatus(error.getHttpCode());
                            response.setContentType("application/json");

                            ApiErrorResponse body = new ApiErrorResponse(
                                    error.getHttpCode(),
                                    error.getMessage()
                            );

                            response.getWriter().write(
                                    new ObjectMapper().writeValueAsString(body)
                            );
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            ApiErrorCode error = ApiErrorCode.FORBIDDEN;

                            response.setStatus(error.getHttpCode());
                            response.setContentType("application/json");

                            ApiErrorResponse body = new ApiErrorResponse(
                                    error.getHttpCode(),
                                    error.getMessage()
                            );

                            response.getWriter().write(
                                    new ObjectMapper().writeValueAsString(body)
                            );
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        // PUBLIC
                        .requestMatchers("/api/auth/init-data","/api/auth/login").permitAll()

                        // PROTECTED
                        .requestMatchers("/api/auth/ubah-password-sendiri","/api/pegawai/**").authenticated()

                        // lainnya wajib login
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

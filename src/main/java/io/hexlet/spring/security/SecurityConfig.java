package io.hexlet.spring.security;
import io.hexlet.spring.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // логин
                        .requestMatchers("/api/login")
                        .permitAll()

                        // регистрация
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/users"
                        )
                        .permitAll()

                        // просмотр постов
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/posts/**"
                        )
                        .permitAll()

                        // просмотр тегов
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tags/**"
                        )
                        .permitAll()

                        // всё остальное требует JWT
                        .anyRequest()
                        .authenticated()
                );

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
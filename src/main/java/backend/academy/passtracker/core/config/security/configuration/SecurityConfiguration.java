package backend.academy.passtracker.core.config.security.configuration;

import backend.academy.passtracker.core.config.security.filter.JwtAuthenticationFilter;
import backend.academy.passtracker.rest.model.common.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Lazy
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PATCH", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public List<String> unprotectedEndpoints() {
        return List.of(
                "/auth/**",
                "/group/{groupId}",
                "/group/list"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/pass/request/pageable",
                                "/user"
                        ).hasAnyRole("ADMIN", "DEANERY", "TEACHER")
                        .requestMatchers(
                                "/group",
                                "/user/{userId}/role",
                                "/dean/**"
                        ).hasAnyRole("ADMIN", "DEANERY")
                        .requestMatchers(
                                "/pass/request",
                                "/pass/request/extend"
                        ).hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers(
                                "/pass/request/{passRequestId}",
                                "/pass/request/extend/{requestId}"
                        ).hasAnyRole("ADMIN", "STUDENT", "TEACHER", "DEANERY")
                        .requestMatchers("/report").hasAnyRole("ADMIN", "TEACHER", "DEAN")
                        .requestMatchers(unprotectedEndpoints().toArray(new String[]{})).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/v3/api-docs/**", "/configuration/**",
                        "/swagger-ui/**", "/swagger-resources/**",
                        "/swagger-ui.html", "/api-docs/**");
    }
}
package org.nahajski.baseauth.security;

import org.nahajski.baseauth.entity.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final String frontendUrl;
    private final String adminPattern;
    private final String unauthenticatedPattern;

    public SecurityConfig(OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler, @Value("${baseauth.frontend.url}") String frontendUrl,
                          @Value("${baseauth.pattern.unauthenticated}") String unauthenticatedPattern, @Value("${baseauth.pattern.admin}") String adminPattern) {
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.frontendUrl = frontendUrl;
        this.adminPattern = adminPattern;
        this.unauthenticatedPattern = unauthenticatedPattern;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        authorise -> authorise.requestMatchers(adminPattern).hasRole(UserRole.ADMIN.toString())
                                .requestMatchers(unauthenticatedPattern).permitAll()
                                .anyRequest().authenticated())
                .oauth2Login(oauth2 -> {
                    oauth2.successHandler(oAuth2LoginSuccessHandler);
                    oauth2.loginPage("/login").permitAll();
                }).exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(new FailedAuthResponse());
                }).logout(logout -> logout.logoutSuccessHandler(new LogoutResponse()))

        ;

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
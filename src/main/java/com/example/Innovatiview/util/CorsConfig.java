package com.example.Innovatiview.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "http://localhost:8081",
                                "http://localhost:8080",
                                "http://localhost:8082", // Expo web development server
                                "http://127.0.0.1:8081",
                                "http://127.0.0.1:8080",
                                "http://127.0.0.1:8082",
                                "http://localhost:19006", // Expo development server
                                "http://127.0.0.1:19006",
                                "http://localhost:19000", // Expo dev tools
                                "http://127.0.0.1:19000",
                                "exp://localhost:8081", // Expo app
                                "exp://127.0.0.1:8081")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

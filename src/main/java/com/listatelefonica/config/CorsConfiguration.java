package com.listatelefonica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("https://lukazdev-app.herokuapp.com") // <- configurar acesso
                    .exposedHeaders("Location")
                    .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("Origin", "Content-Type", "Accept");
            }
        };
    }
}

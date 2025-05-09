package com.ecommerce.challenge.infrastructure.adapter.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // allow all endpoints
                .allowedOrigins("http://localhost:5173") //TODO: get value from properties file
                .allowedMethods("GET", "POST", "PUT", "DELETE") // specify allowed methods
                .allowedHeaders("*"); // allow all headers
    }
}

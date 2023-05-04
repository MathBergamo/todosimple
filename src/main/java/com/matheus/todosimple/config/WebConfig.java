package com.matheus.todosimple.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {//implementar o WebSvc para identificar para o Spring que será uma config Spring

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}

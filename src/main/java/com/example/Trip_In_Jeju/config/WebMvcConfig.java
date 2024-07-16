package com.example.Trip_In_Jeju.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:///" + fileDirPath + "/");
    }
}
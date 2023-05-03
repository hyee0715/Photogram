package com.hy.photogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // Web 설정 파일

    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/upload/**")   // jsp 페이지에서 '/upload/**'와 같은 주소 패턴이 요청되면 실행
                .addResourceLocations("file:///" + uploadFolder)
                .setCachePeriod(60 * 10 * 6)    // 1시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}

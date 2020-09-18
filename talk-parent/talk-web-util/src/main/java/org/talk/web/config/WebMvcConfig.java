package org.talk.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.talk.web.interceptor.ContextInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public ContextInterceptor contextInterceptor() {
        return new ContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor());
    }
}

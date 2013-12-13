package com.ninja_squad.poneyserver.web.config;

import com.ninja_squad.poneyserver.web.AuthenticationInterceptor;
import com.ninja_squad.poneyserver.web.WebPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The Spring application configuration
 * @author JB Nizet
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebPackage.class)
public class AppConfig extends WebMvcConfigurerAdapter {
    /**
     * Adds an interceptor to handle authentication
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**/*")
                .excludePathPatterns("/users", "/authentication");
    }

    @Bean
    public HandlerInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}

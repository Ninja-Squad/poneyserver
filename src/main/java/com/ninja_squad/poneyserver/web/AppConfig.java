package com.ninja_squad.poneyserver.web;

import com.ninja_squad.poneyserver.web.security.AuthenticationInterceptor;
import com.ninja_squad.poneyserver.web.security.CorsFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Spring web application configuration
 * @author JB Nizet
 */
@EnableAutoConfiguration
@EnableSwagger2
@ComponentScan
@Configuration
@Import({WebSocketConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter {

    /**
     * Adds an interceptor to handle authentication. All the HTTP requests must have a header Custom-Authentication with their
     * login as value to be able to access the resource. Otherwise, a 401 response is sent back. The only URLs that are
     * not intercepted are /users (used to register) and /authentication (used to authenticate)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/api/**/*")
                .excludePathPatterns("/api/users", "/api/authentication");
    }

    @Bean
    public HandlerInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfo("PoneyServer", "The backend for your awesome PoneyRacer application", "2.0", null, "contact@ninja-squad.com", null, null))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api/.*"))
            .build()
            .pathMapping("/")
            .genericModelSubstitutes(ResponseEntity.class)
            .useDefaultResponseMessages(false);
    }
}

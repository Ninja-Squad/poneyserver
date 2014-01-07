package com.ninja_squad.poneyserver.web.config;

import com.mangofactory.swagger.configuration.DocumentationConfig;
import com.ninja_squad.poneyserver.web.WebPackage;
import com.ninja_squad.poneyserver.web.security.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * The Spring web application configuration
 * @author JB Nizet
 */
@Configuration
@EnableWebMvc
@EnableWebSocketMessageBroker
@ComponentScan(basePackageClasses = WebPackage.class)
@Import({PropertyPlaceholderConfig.class, DocumentationConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the websocket endpoint "/race" with SockJS support. Clients may thus connect using
     * <code>new SockJS("/[context-path]/race")</code>.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/race").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
    }

    /**
     * Configures the websocket message broker with the path "/topic". The positions of every poney during a running
     * race are broadcasted to /topic/[id of the race]. Clients interested in the positions of the poneys of the race
     * 12 must thus subscribe using <code>stompClient.subscribe('/topic/12', callback)</code>.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    /**
     * Adds an interceptor to handle authentication. All the HTTP requests must have a header Custom-Authentication with their
     * login as value to be able to access the resource. Otherwise, a 401 response is sent back. The only URLs that are
     * not intercepted are /users (used to register) and /authentication (used to authenticate)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**/*")
                .excludePathPatterns("/users", "/authentication", "/api-docs*", "/api-docs/**/*", "/swagger/**/*");
    }

    @Bean
    public HandlerInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}

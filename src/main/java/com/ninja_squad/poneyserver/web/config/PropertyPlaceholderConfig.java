package com.ninja_squad.poneyserver.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Config class used to load properties. Yes, it must be separate from the main config and import in order to work
 * properly.
 */
@Configuration
@PropertySource("classpath:swagger.properties")
public class PropertyPlaceholderConfig {

    protected PropertyPlaceholderConfig() {
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
package zhiganov.textractor.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application")
@EnableConfigurationProperties(ConfigProperties.class)
public class AutoConfigProperties {

    // @Bean
    // public DocumentService loggingAspect(ConfigProperties properties){
    //     return new DocumentService(properties);
    // }
}

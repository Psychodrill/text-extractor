package zhiganov.TextExtractor.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class AutoConfigProperties {

    // @Bean
    // public DocumentService loggingAspect(ConfigProperties properties){
    //     return new DocumentService(properties);
    // }
}

package zhiganov.textractor.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
//@Configuration
@ConfigurationProperties("application")
public class ConfigProperties {

    private  String uploadpath;

    private  ImageExtensions extensions;

    @Data
    public static class ImageExtensions{
        private List<String> extensions;
    }

}

package zhiganov.TextExtractor.service;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import lombok.Data;
import zhiganov.TextExtractor.model.*;
@Data
@Service
@ConfigurationProperties("application.image")
public class DocumentExtractorFactory {

    List<String> extensions;
    private static final Map<String, IDataExtractor> extractorsCache = new HashMap<>();

    @Autowired
    private DocumentExtractorFactory(List<IDataExtractor> extractors) {
        for(IDataExtractor extractor : extractors) {
            extractorsCache.put(extractor.getType(), extractor);
        }
    }
    
    public IDataExtractor getExtractor(String type) {
        IDataExtractor extractor= null;
        if(extensions.contains(type)){
            extractor = extractorsCache.get("image");
        }
        else{
            extractor = extractorsCache.get(type);
        }
        //if(extractor == null) throw new RuntimeException("Unknown extractor type: " + type);
        return extractor;
    }

}

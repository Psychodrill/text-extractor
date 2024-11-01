package zhiganov.TextExtractor.service;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhiganov.TextExtractor.model.*;

@Service
public class DocumentServiceFactory {


    private static final Map<String, IDataExtractor> extractorsCache = new HashMap<>();

    @Autowired
    private DocumentServiceFactory(List<IDataExtractor> extractors) {
        for(IDataExtractor extractor : extractors) {
            extractorsCache.put(extractor.getType(), extractor);
        }
    }

    public static IDataExtractor getExtractor(String type) {
        IDataExtractor extractor = extractorsCache.get(type);
        if(extractor == null) throw new RuntimeException("Unknown extractor type: " + type);
        return extractor;
    }

}

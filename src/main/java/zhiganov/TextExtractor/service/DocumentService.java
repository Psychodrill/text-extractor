package zhiganov.TextExtractor.service;

import org.springframework.stereotype.Service;

import zhiganov.TextExtractor.model.Document;

@Service
public class DocumentService {

    public String recognize(Document doc) {
        return doc.getContent();
    }

}

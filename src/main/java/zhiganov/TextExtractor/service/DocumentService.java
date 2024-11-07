package zhiganov.TextExtractor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import zhiganov.TextExtractor.model.Document;
import zhiganov.TextExtractor.repository.IDocumentRepository;

import com.mongodb.DBObject;



@Service

public class DocumentService {


    //@Autowired
    private final IDocumentRepository docRepository;

    public DocumentService (IDocumentRepository docRepository){
        this.docRepository=docRepository;
    }


    public Optional<Document> findById(String id) {
        return docRepository.findById(id);
    }


    public Document addDocument(MultipartFile file) {

        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setDate(LocalDate.now());

        try {
            doc.setContent(file.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return docRepository.insert(doc);
    }

}

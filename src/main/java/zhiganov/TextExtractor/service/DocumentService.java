package zhiganov.TextExtractor.service;

//import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import zhiganov.TextExtractor.exception.NotFoundExtractorException;
import zhiganov.TextExtractor.factory.DocumentExtractorFactory;

import zhiganov.TextExtractor.model.Document;
import zhiganov.TextExtractor.model.IDataExtractor;
import zhiganov.TextExtractor.repository.IDocumentRepository;

@Service
@ConfigurationProperties("application")
public class DocumentService {

    private String uploadpath;
    //private String uploadpath = "src/main/resources/upload";
    private final IDocumentRepository docRepository;
    private final DocumentExtractorFactory docExtractorFactory;

    public DocumentService (IDocumentRepository docRepository, DocumentExtractorFactory docExtractorFactory){
        this.docRepository=docRepository;
        this.docExtractorFactory= docExtractorFactory;
    }

    public Optional<Document> findById(String id) {
        return docRepository.findById(id);
    }

   //@Logging
    public Document acceptDocument(MultipartFile file) throws Exception{

        File tempFile =saveTempFile(file, uploadpath);

        IDataExtractor dataExtractor=null;
        String name = file.getOriginalFilename();
        String [] stringArray= name.split("\\.");
        String type = stringArray[stringArray.length-1];
        dataExtractor =docExtractorFactory.getExtractor(type);
        if(dataExtractor == null) throw new NotFoundExtractorException("Unknown extractor type: " + type);

        String recognized = dataExtractor.extractText(tempFile.getPath());
        
        return saveFileinDB(file, recognized);
    }

    private File saveTempFile(MultipartFile file, String uploadPath) throws IOException{

        File directory = new File(uploadpath); 
        if(!directory.exists()){
            directory.mkdir();
        }
        String filePath=String.format("%s/%s",directory,file.getOriginalFilename());
        File tempFile = new File(filePath);
        OutputStream os = new FileOutputStream(tempFile);
        os.write(file.getBytes());
        return tempFile;

    }
    private Document saveFileinDB(MultipartFile file, String recognized) throws IOException{
        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setDate(LocalDate.now());
       
        //doc.setContent(file.getBytes());
        doc.setRecognized(recognized);
        return docRepository.insert(doc);

    }

    public void deleteAll() {
        docRepository.deleteAll();
    }

    public List<Document> findAll() {
        return docRepository.findAll();
    }

}

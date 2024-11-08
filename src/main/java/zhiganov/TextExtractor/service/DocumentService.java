package zhiganov.TextExtractor.service;

//import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import zhiganov.TextExtractor.model.Document;
import zhiganov.TextExtractor.model.IDataExtractor;
import zhiganov.TextExtractor.model.NotFoundExtractorException;
import zhiganov.TextExtractor.repository.IDocumentRepository;

import lombok.Data;


@Data
@Service
@ConfigurationProperties("application")
public class DocumentService {


    private String uploadpath;
    //@Autowired
    private final IDocumentRepository docRepository;
    private final DocumentExtractorFactory docServiceFactory;

    public DocumentService (IDocumentRepository docRepository, DocumentExtractorFactory docServiceFactory){
        this.docRepository=docRepository;
        this.docServiceFactory= docServiceFactory;
    }


    public Optional<Document> findById(String id) {
        return docRepository.findById(id);
    }


    public Document acceptDocument(MultipartFile file) throws Exception{


        File tempFile =saveTempFile(file, uploadpath);

        IDataExtractor dataExtractor=null;
        String name = file.getOriginalFilename();
        String [] stringArray= name.split("\\.");
        String type = stringArray[stringArray.length-1];
        dataExtractor =docServiceFactory.getExtractor(type);
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

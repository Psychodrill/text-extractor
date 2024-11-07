package zhiganov.TextExtractor.service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import zhiganov.TextExtractor.model.Document;
import zhiganov.TextExtractor.model.IDataExtractor;
import zhiganov.TextExtractor.repository.IDocumentRepository;

import com.mongodb.DBObject;



@Service
@ConfigurationProperties("application")
public class DocumentService {


    private String uploadpath;
    //@Autowired
    private final IDocumentRepository docRepository;
    private final DocumentServiceFactory docServiceFactory;

    public DocumentService (IDocumentRepository docRepository, DocumentServiceFactory docServiceFactory){
        this.docRepository=docRepository;
        this.docServiceFactory= docServiceFactory;
    }


    public Optional<Document> findById(String id) {
        return docRepository.findById(id);
    }


    public String acceptDocument(MultipartFile file) throws Exception{


        saveTempFile(file, uploadpath);

        IDataExtractor dataExtractor=null;
        String name = file.getOriginalFilename();
        String [] stringArray= name.split("\\.");
        String type = stringArray[stringArray.length-1];
        dataExtractor =docServiceFactory.getExtractor(type);
        if(dataExtractor == null) throw new RuntimeException("Unknown extractor type: " + type);

        saveFileinDB(file);

                    
       // try{

        //}
        //catch(Exception e){
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload file with extension " +type + " " + e.getMessage());
       // }
        //final String recognized = dataExtractor.extractText(filePath);
        //return ResponseEntity.status(HttpStatus.ACCEPTED).body(recognized);


        return dataExtractor.extractText(uploadpath);
    }

    private void saveTempFile(MultipartFile file, String uploadPath) throws IOException{

        File directory = new File(uploadpath); 
        if(!directory.exists()){
            directory.mkdir();
        }
        String filePath=String.format("%s/%s",directory,file.getOriginalFilename());
        File tempFile = new File(filePath);
        OutputStream os = new FileOutputStream(tempFile);
        os.write(file.getBytes());


    }
    private void saveFileinDB(MultipartFile file) throws IOException{
        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setDate(LocalDate.now());
       
        doc.setContent(file.getBytes());
       
        docRepository.insert(doc);
    }

}

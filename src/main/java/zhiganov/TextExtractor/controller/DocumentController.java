package zhiganov.TextExtractor.controller;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

//import java.io.IOException;
//import java.nio.file.*;
import java.io.*;
import java.nio.file.Files;

import zhiganov.TextExtractor.model.Document;
import zhiganov.TextExtractor.service.*;


@RestController
@RequestMapping("/documents")
//@RequiredArgsConstructor
@Tag(name="Documents", description=" API for document service")
public class DocumentController {

    @Value("${upload-path}")
    private String uploadPath;

    private final DocumentService docService;
    private final DocumentServiceFactory docServiceFactory;

    public DocumentController(DocumentService docService, DocumentServiceFactory docServiceFactory){
        this.docService=docService;
        this.docServiceFactory= docServiceFactory;
    }

    @GetMapping("/{id}")
    public String getDataById(@PathVariable Long id){

        //       Optional<Timesheet> ts =  service.findById(id);
        // if(ts.isPresent()){
        //     //return ResponseEntity.ok().body(ts.get());
        //     return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        // }
        // return ResponseEntity.notFound().build();
        return "test string";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file){

       // String directory = uploadPath;
       File directory = new File(uploadPath);

       
        try{
            if(!directory.exists()){
                directory.mkdir();
            }

            String filePath=String.format("%s/%s",directory,file.getOriginalFilename());
            File tempFile = new File(filePath);

            try (OutputStream os = new FileOutputStream(tempFile)) {
                os.write(file.getBytes());
            }
            // Path filePath = directory.resolve(file.getOriginalFilename());
            // Files.write(filePath, file.getBytes());
            final String recognized = docService.recognize(filePath);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(recognized);
        }
        catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file" + e.getMessage());
        }


        
        
        
        
        
       //return ResponseEntity.status(HttpStatus.ACCEPTED).body("recognized");

    }   

}

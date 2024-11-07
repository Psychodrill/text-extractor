package zhiganov.TextExtractor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.*;
import java.util.*;

import zhiganov.TextExtractor.model.Document;
import zhiganov.TextExtractor.model.IDataExtractor;
import zhiganov.TextExtractor.service.*;

@Data
@RestController
@RequestMapping("/documents")
//@RequiredArgsConstructor
@Tag(name="Documents", description=" API for document service")

//@ConfigurationProperties("application")

public class DocumentController {


    //private String uploadpath;
    //@Autowired
    private final DocumentService docService;
    //@Autowired
    private final DocumentServiceFactory docServiceFactory;
    //@Autowired
    //private final IDocumentRepository docRepository;

    public DocumentController(DocumentService docService, DocumentServiceFactory docServiceFactory){
        this.docService=docService;
        this.docServiceFactory= docServiceFactory;
        //this.docRepository=docRepository;
    }

    @GetMapping("/{id}")
    public String getDataById(@PathVariable String id){

        Optional<Document> doc =  docService.findById(id);
        // if(ts.isPresent()){
        //     //return ResponseEntity.ok().body(ts.get());
        //     return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        // }
        // return ResponseEntity.notFound().build();
        return "test string";
    }

    @Operation(
        summary = "Upload file",
        description = "Upload File ant Returns ?",
        responses = {
            @ApiResponse(description = "Success", responseCode="202", content =@Content(schema =@Schema(implementation = String.class))),
            @ApiResponse(description = "Bad Request",responseCode="400", content =@Content(schema =@Schema(implementation = Void.class))),
            @ApiResponse(description = "Internal Server Error",responseCode="500", content =@Content(schema =@Schema(implementation = Void.class)))       
        }
    )
    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file){

         //File directory = new File(uploadpath);     
        // String name = file.getOriginalFilename();
        // var stringArray= name.split("\\.");
        // String type = stringArray[stringArray.length-1];
    
        try{

            // String filePath=String.format("%s/%s",directory,file.getOriginalFilename());
            // File tempFile = new File(filePath);

            // OutputStream os = new FileOutputStream(tempFile);
            // os.write(file.getBytes());
            
            final String recognized =docService.acceptDocument(file);
            //final String recognized = dataExtractor.extractText(filePath);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(recognized);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file" + e.getMessage());
        }

    }   

}

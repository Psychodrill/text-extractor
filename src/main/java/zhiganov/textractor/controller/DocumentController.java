package zhiganov.textractor.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import zhiganov.textractor.exception.NotFoundExtractorException;
import zhiganov.textractor.model.*;
import zhiganov.textractor.service.*;

import java.util.*;

//@Data
@RestController
@RequestMapping("/documents")
@Tag(name="Documents", description=" API for document service")

public class DocumentController {

    private final DocumentService docService;

    public DocumentController(DocumentService docService){
        this.docService=docService;

    }

    @Operation(
        summary = "Get File By Id",
        description = "Returns File By Id",
        responses = {
            @ApiResponse(description = "Success", responseCode="200", content =@Content(schema =@Schema(implementation = Document.class))),
            @ApiResponse(description = "Not Found",responseCode="404", content =@Content(schema =@Schema(implementation = Void.class)))
           
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable String id){
        Optional<Document> optDoc =  docService.findById(id);
        if(optDoc.isPresent()){
            //return ResponseEntity.ok().body(ts.get());
            return ResponseEntity.status(HttpStatus.OK).body(optDoc.get());
        }
        return ResponseEntity.notFound().build();

    }

    @Operation(
        summary = "Get All Files",
        description = "Returns All Recognized Documents",
        responses = {
            @ApiResponse(description = "Success", responseCode="200", content =@Content(schema =@Schema(implementation = List.class))),
            @ApiResponse(description = "Not Found",responseCode="404", content =@Content(schema =@Schema(implementation = Void.class)))
           
        }
    )
    @GetMapping("/")
    public ResponseEntity<List<Document>> findAll(){
        Optional<List<Document>> optDoc =  Optional.of(docService.findAll());
        if(optDoc.isPresent()){
            //return ResponseEntity.ok().body(ts.get());
            return ResponseEntity.status(HttpStatus.OK).body(optDoc.get());
        }
        return ResponseEntity.notFound().build();

    }

    @Operation(
        summary = "Upload file",
        description = "Upload File ant Returns Document in DB",
        responses = {
            @ApiResponse(description = "Accepted", responseCode="202", content =@Content(schema =@Schema(implementation = Document.class))),
            @ApiResponse(description = "Bad Request",responseCode="400", content =@Content(schema =@Schema(implementation = Void.class))),
            @ApiResponse(description = "Internal Server Error",responseCode="500", content =@Content(schema =@Schema(implementation = Void.class)))       
        }
    )
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadData(@RequestParam("file") MultipartFile file){
    
        try{
            // String recognized = docService.uploadPath();
            // return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Document());
            final Document recognized =docService.acceptDocument(file);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(recognized);
        }
        catch(NotFoundExtractorException e){
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload file with this extension " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(Exception e){
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something gone wrong " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

     }
    @Operation(
        summary = "Delete All",
        description = "Delete All Received Documents From DB",
        responses = {
            @ApiResponse(description = "No Content",responseCode="204", content =@Content(schema =@Schema(implementation = Void.class)))
           
        }
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        docService.deleteAll();

        return ResponseEntity.noContent().build();

    }

}

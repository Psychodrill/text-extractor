package zhiganov.TextExtractor.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import zhiganov.TextExtractor.model.Document;
import zhiganov.TextExtractor.service.DocumentService;

@RestController
@RequestMapping("/documents")
//@RequiredArgsConstructor
@Tag(name="Documents", description=" API for document service")
public class DocumentController {

    private final DocumentService docService;

    public DocumentController(DocumentService docService){
        this.docService=docService;
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

    @PostMapping
    public ResponseEntity<String> extractData(@RequestBody Document doc){

        final String recognized = docService.recognize(doc);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(recognized);
        //return recognized;

    }   

}

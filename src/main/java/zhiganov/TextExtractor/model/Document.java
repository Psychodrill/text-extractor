package zhiganov.TextExtractor.model;



import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;

import org.bson.types.Binary;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.web.multipart.MultipartFile;

//import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
//@Entity
//@Table(name="document")
//@Document(collection="document")
public class Document {


    @Id
    //@Generated(strategy = GenerationType.SEQUENCE)
    //@Genera
    //@EqualsAndHashCode.Include
    @Indexed
    private String id;
    private String name;
    private LocalDate date;
    private byte [] content;
    //private MultipartFile content;
    //private File content;
    //private org.bson.Document content;


    
}

package zhiganov.TextExtractor.model;

import java.time.LocalDate;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

@Data
public class Document {

    @Id
    @Indexed
    private String id;
    private String name;
    private LocalDate date;
    private String recognized;
    
}

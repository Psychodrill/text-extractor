package zhiganov.TextExtractor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="document")
public class Document  {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;
    private String content;


    // public ICollection<Stream> ImageStreams { get; set; }
    // protected Document(string inputFileName, string outputFileName)
    // {
    //     InputFileName = inputFileName;
    //     OutputFileName = outputFileName;
    //     ImageStreams = new List<Stream>();
    // }

    // public string InputFileName {get; private set;}
    // public string OutputFileName { get; private set; }


    // public abstract ICollection<Stream> ExtractData();
    // public abstract void SaveFile(MemoryStream file, string resultOutName, string imgFormat);
    //public abstract String ExtractData();
    //public abstract Long SaveData();

    
}

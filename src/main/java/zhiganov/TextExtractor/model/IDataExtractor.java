package zhiganov.TextExtractor.model;

import java.io.File;
import java.io.IOException;

public interface IDataExtractor {

    // public interface IImageTextExtractor {
    //     String imageExtractData();

    // }
    // public interface IPdfTextExtractor {
    //     String pdfExtractData();

    // }
    // public interface IWordTextExtractor {
    //     String wordExtractData();

    // }

    String extractText(String filePath);
    String getType();


}

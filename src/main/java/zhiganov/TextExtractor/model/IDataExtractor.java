package zhiganov.TextExtractor.model;


public interface IDataExtractor {

    String extractText(String filePath);
    String getType();

}

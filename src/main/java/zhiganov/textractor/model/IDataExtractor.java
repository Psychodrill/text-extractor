package zhiganov.textractor.model;


public interface IDataExtractor {

    String extractText(String filePath);
    String getType();

}

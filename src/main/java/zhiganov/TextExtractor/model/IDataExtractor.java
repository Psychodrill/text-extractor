package zhiganov.TextExtractor.model;

public abstract interface IDataExtractor {

    public interface IImageTextExtractor {
        String extractData();

    }
    public interface IPdfTextExtractor {
        String extractData();

    }
    public interface IWordTextExtractor {
        String extractData();

    }

    String getType();


}

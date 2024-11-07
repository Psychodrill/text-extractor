package zhiganov.TextExtractor.model;

public class ExtractorException extends RuntimeException {

    public ExtractorException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}

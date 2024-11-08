package zhiganov.TextExtractor.model;

public class NotFoundExtractorException extends RuntimeException {

    public NotFoundExtractorException(String errorMessage) {
        super(errorMessage);
    }

}

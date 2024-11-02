package zhiganov.TextExtractor.model;

public abstract interface IDataExtractor {

    public interface IImageTextExtractor {
        String imageExtractData();

    }
    public interface IPdfTextExtractor {
        String pdfExtractData();

    }
    public interface IWordTextExtractor {
        String wordExtractData();

    }

    String extractText();
    String getType();


}

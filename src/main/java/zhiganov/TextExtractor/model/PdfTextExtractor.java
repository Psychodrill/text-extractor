package zhiganov.TextExtractor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PdfTextExtractor implements IDataExtractor, IDataExtractor.IImageTextExtractor, IDataExtractor.IPdfTextExtractor{


    private final ImageTextExtractor imageTextExtractor;

    @Override
    public String pdfExtractData() {
        return "recognized text";
    }

    @Override
    public String imageExtractData() {
        imageTextExtractor.imageExtractData();
        return "recognized text";
    }

    @Override
    public String getType() {
        return "pdf";
    }



}

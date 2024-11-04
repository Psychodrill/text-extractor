package zhiganov.TextExtractor.model;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.*;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.filter.MissingImageReaderException;

import org.apache.pdfbox.pdmodel.*;

import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PdfTextExtractor implements IDataExtractor{


    private final IImageTextExtractor imageTextExtractor;

    @Override
    public String getType() {
        return "pdf";
    }

    @Override
    public String extractText(String filePath){

        String result = "";
        
        try{
            PDDocument document = Loader.loadPDF(new File(filePath));
            result+=new PDFTextStripper().getText(document);
            PDPageTree list = document.getPages();
            for (PDPage page : list) {
                PDResources pdResources = page.getResources();
                for (COSName c : pdResources.getXObjectNames()) {
                    try {
                        PDXObject imageObj = pdResources.getXObject(c);
                        if (imageObj instanceof PDImageXObject) {
                            // same image to list
                            BufferedImage bImage = ((PDImageXObject) imageObj).getImage();
                            //acceptedImages.add(bImage);
                            
                            result += imageTextExtractor.imageExtractData(bImage);

                        }
                    } catch (MissingImageReaderException mex) {
                    // log.warn("Missing Image Reader for format: ", mex);
                    
                    }
                }
            }
            
        }
        catch(IOException ex){

        }
        return result;
    }

}

package zhiganov.TextExtractor.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import com.github.librepdf;
//import com.itextpdf.text.pdf.*;
import org.apache.pdfbox.*;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.filter.MissingImageReaderException;
//import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.*;

import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
//import org.apache.pdfbox.tools.ExtractImages;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PdfTextExtractor implements IDataExtractor{


    private final IImageTextExtractor imageTextExtractor;

    // @Override
    // public String pdfExtractData() {
    //     return "recognized text";
    // }

    // @Override
    // public String imageExtractData(String filePath) {
    //     return imageTextExtractor.imageExtractData(filePath);
    //     //return "recognized text";
    // }

    @Override
    public String getType() {
        return "pdf";
    }

    @Override
    public String extractText(String filePath){

        String result = "";
        File file = new File(filePath);
        //filePath="src/main/resources/upload";
        //PdfDocument pdfDoc = PdfDocument.load(new PDFRenderer(file));
        //pdfDoc.
        //File file = new File("/path/to/document.pdf"));
        try{
            
        PDDocument document = Loader.loadPDF(file);

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
                        
                        result += imageTextExtractor.imageExtractData(null, bImage);

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

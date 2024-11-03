package zhiganov.TextExtractor.model;

import java.awt.image.BufferedImage;
import java.io.File;

import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Component
public class ImageTextExtractor implements IDataExtractor, IImageTextExtractor{


    @Override
    public String imageExtractData(String filePath, BufferedImage bufImage) {

        File image = new File(filePath);
        
        Tesseract tesseract = new Tesseract();
        
        tesseract.setDatapath("src/main/resources/tessdata");
        
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(2);
        String result=null;
        try {
            result = tesseract.doOCR(image);
        } catch (TesseractException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getType() {
        //image/png
        //image/jpeg
        //image/gif
        //image/bmp
        
        return "image";
    }

    @Override
    public String extractText(String filePath) {

        return imageExtractData(filePath, null);

    }






    

}

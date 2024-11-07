package zhiganov.TextExtractor.model;

import java.awt.image.BufferedImage;
import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Data
@Component
@ConfigurationProperties("tesseract")
public class ImageTextExtractor implements IDataExtractor, IImageTextExtractor{

    private String recognized ="";
    private Tesseract tesseract = new Tesseract();
    private String datapath;
    private String language;
    private int pagemode;
    private int enginemode;

    private void setTesseractOptions(){
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
        tesseract.setPageSegMode(pagemode);
        tesseract.setOcrEngineMode(enginemode);
    }
    @Override
    public String imageExtractData(String filePath) {

        setTesseractOptions();
        File image = new File(filePath);
        
        try {
            recognized += tesseract.doOCR(image);
        } catch (TesseractException e) {

            e.printStackTrace();
            throw new ExtractorException(e.getMessage(), e);
        }
        return recognized;
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

        return imageExtractData(filePath);

    }

    @Override
    public String imageExtractData(BufferedImage bufImage) {
        setTesseractOptions();
        try {
            recognized += tesseract.doOCR(bufImage);
        } catch (TesseractException e) {

            e.printStackTrace();
            throw new ExtractorException(e.getMessage(), e);
        }
        return recognized;
    }







    

}

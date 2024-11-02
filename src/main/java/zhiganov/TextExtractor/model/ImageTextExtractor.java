package zhiganov.TextExtractor.model;

import org.springframework.stereotype.Component;

@Component
public class ImageTextExtractor implements IDataExtractor, IDataExtractor.IImageTextExtractor{



    @Override
    public String imageExtractData() {
        return "recognized text";
    }

    @Override
    public String getType() {
        //image/png
        //image/jpeg
        //image/gif
        //image/bmp
        
        return "image";
    }




    

}

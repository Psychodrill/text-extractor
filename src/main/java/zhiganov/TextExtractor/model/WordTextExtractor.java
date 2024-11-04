package zhiganov.TextExtractor.model;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.extractor.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

@Component
@RequiredArgsConstructor
public class WordTextExtractor implements IDataExtractor{

    private final IImageTextExtractor imageTextExtractor;
    @Override
    public String getType() {
        return "docx";
    }

    @Override
    public String extractText(String filePath){

        String result = "";
        File file =new File(filePath);
        try{
            XWPFDocument docx= new XWPFDocument(new FileInputStream(file));
            XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
            result+=extractor.getText();
            List<XWPFPictureData> pictures =docx.getAllPictures();
            for(XWPFPictureData pic :pictures){
                byte[] bytepic= pic.getData();
                BufferedImage image=  ImageIO.read(new ByteArrayInputStream(bytepic));
                result +=imageTextExtractor.imageExtractData(image);
            }
            
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        


        return result;
    }


}

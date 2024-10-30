package zhiganov.TextExtractor.service;

import java.nio.file.Path;

import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import zhiganov.TextExtractor.model.Document;
import java.nio.file.*;
import java.io.*;

@Service
public class DocumentService {

    public String recognize(String filePath) {

        File image = new File(filePath);
        System.out.println("1");
        Tesseract tesseract = new Tesseract();
        System.out.println("1");
        tesseract.setDatapath("src/main/resources/tessdata");
        System.out.println("1");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        System.out.println("1");
        String result=null;
        try {
            result = tesseract.doOCR(image);
        } catch (TesseractException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}

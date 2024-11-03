package zhiganov.TextExtractor.model;

import java.awt.image.BufferedImage;

public interface IImageTextExtractor {

 String imageExtractData(String filePath, BufferedImage bufImage);

}

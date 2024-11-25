package zhiganov.textractor.model;

import java.awt.image.BufferedImage;

public interface IImageTextExtractor {

 String imageExtractData(String filePath);
 String imageExtractData(BufferedImage bufImage);

}

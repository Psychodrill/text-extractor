package zhiganov.TextExtractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
//import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import zhiganov.TextExtractor.repository.IDocumentRepository;


@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IDocumentRepository.class)
//@SpringBootApplication(exclude="zhiganov.TextExtractor.repository")
public class TextExtractorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextExtractorApplication.class, args);
	}

}

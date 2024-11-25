package zhiganov.textractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import zhiganov.textractor.repository.IDocumentRepository;


@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IDocumentRepository.class)
//@SpringBootApplication(exclude="zhiganov.TextExtractor.repository")
public class TextractorApplication {

	public static void main(String[] args) {	
		SpringApplication.run(TextractorApplication.class, args);
	}

}

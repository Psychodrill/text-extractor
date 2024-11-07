package zhiganov.TextExtractor.repository;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import zhiganov.TextExtractor.model.Document;
//@EnableMongoRepositories(basePackages = "zhiganov.TextExtractor.repository")
@Repository
// @Component
public interface IDocumentRepository extends MongoRepository<Document, String> {

  //public List<Document> findAll();
  //public List<Customer> findByLastName(String lastName);
}

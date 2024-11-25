package zhiganov.textractor.repository;

//import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import zhiganov.textractor.model.Document;

@Repository
public interface IDocumentRepository extends MongoRepository<Document, String> {


}

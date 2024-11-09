package zhiganov.TextExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import zhiganov.TextExtractor.repository.IDocumentRepository;
import zhiganov.TextExtractor.model.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerTest {

    @Autowired
    IDocumentRepository docRepository;

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach(){
        restClient = RestClient.create("http://localhost:" + port);
        docRepository.deleteAll();
    }

    @Test
    void getDocumentByIdTest(){
        //Arrange
        Document expected = new Document();
        expected.setName("test file.png");
        expected.setDate(LocalDate.now());
        expected.setRecognized("some test recognized text of png");
        expected = docRepository.save(expected);
        //Act
        ResponseEntity<Document> actual = restClient.get()
            .uri("/documents/" + expected.getId())
            .retrieve()
            .toEntity(Document.class);

        //Assert
        assertEquals(HttpStatus.OK, actual.getStatusCode());

        Document responseBody = actual.getBody(); 
        assertNotNull(responseBody);
        assertEquals(expected.getId(), responseBody.getId());
        assertEquals(expected.getName(), responseBody.getName());
        assertEquals(expected.getDate(), responseBody.getDate());
        assertEquals(expected.getRecognized(), responseBody.getRecognized());

    }
    @Test
    void uploadDataTest(){
        //Arrange
        Document toCreate = new Document();
        toCreate.setName("test file.jpg");
        toCreate.setDate(LocalDate.now());
        toCreate.setRecognized("some test recognized text of jpg");
        toCreate = docRepository.save(toCreate);

        //Act
        ResponseEntity<Document> response = restClient.post()
            .uri("/documents/upload")
            .body(toCreate)
            .retrieve()
            .toEntity(Document.class);

        //Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        Document responseBody = response.getBody(); 
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(toCreate.getName(), responseBody.getName());
        assertEquals(toCreate.getDate(), responseBody.getDate());
        assertEquals(toCreate.getRecognized(), responseBody.getRecognized());

        assertTrue(docRepository.existsById(responseBody.getId()));
    }

    @Test
    void findAllTest(){
        //Arrange
        Document expected = new Document();
        expected.setName("test file.pdf");
        expected.setDate(LocalDate.now());
        expected.setRecognized("some test recognized text of pdf");
        expected = docRepository.save(expected);
        Document expected1 = new Document();
        expected1.setName("test file.docx");
        expected1.setDate(LocalDate.now());
        expected1.setRecognized("some test recognized text of docx");
        expected1 = docRepository.save(expected1);
        //Act
        ResponseEntity<List<Document>> actual = restClient.get()
            .uri("/documents/")
            .retrieve()
            .toEntity( new ParameterizedTypeReference<List<Document>>(){});

        //Assert
        assertEquals(HttpStatus.OK, actual.getStatusCode());

        List<Document> responseBody = actual.getBody(); 
        assertNotNull(responseBody);
        assertEquals(expected.getId(), responseBody.get(0).getId());
        assertEquals(expected.getName(), responseBody.get(0).getName());
        assertEquals(expected.getDate(), responseBody.get(0).getDate());
        assertEquals(expected.getRecognized(), responseBody.get(0).getRecognized());

        assertEquals(expected1.getId(), responseBody.get(1).getId());
        assertEquals(expected1.getName(), responseBody.get(1).getName());
        assertEquals(expected1.getDate(), responseBody.get(1).getDate());
        assertEquals(expected1.getRecognized(), responseBody.get(1).getRecognized());

    }
    @Test
    void deleteAllTest(){

        Document toDelete = new Document();
        toDelete.setName("test file.pdf");
        toDelete.setDate(LocalDate.now());
        toDelete.setRecognized("some test recognized text of pdf");
        toDelete = docRepository.save(toDelete);
        Document toDelete1 = new Document();
        toDelete1.setName("test file.docx");
        toDelete1.setDate(LocalDate.now());
        toDelete1.setRecognized("some test recognized text of docx");
        toDelete1 = docRepository.save(toDelete1);
        //Act
        ResponseEntity<Void> response = restClient.delete()
            .uri("/documents")
            .retrieve()
            .toBodilessEntity();

        //Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(docRepository.existsById(toDelete.getId()));
        assertFalse(docRepository.existsById(toDelete1.getId()));

    }
}


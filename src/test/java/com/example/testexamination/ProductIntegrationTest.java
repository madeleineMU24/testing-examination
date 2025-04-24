package com.example.testexamination;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;




//använder en särskild databas för tester som är application-test.properties
//så det är separerat från den riktiga databasen
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test //test som kontrollerar om en produkt kan skapas och hämtas korrekt med ett HTTP anrop
    public void testCreateAndGetProductByHttp(){

        //Arrange
        Product product = new Product(null, "computer", 200);

        //Act
        ResponseEntity<Product> postResponse = restTemplate.postForEntity("http://localhost:" + port + "/products",
                product, Product.class);

        //Assert
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        //Act
        Long productId = postResponse.getBody().getId();

        ResponseEntity<Product> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/products/" + productId, Product.class);

        //Assert
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        assertEquals("computer", getResponse.getBody().getName());
        assertEquals(200, getResponse.getBody().getPrice());
    }


}

package com.example.testexamination;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


//COMPONENT-TESTS


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @MockitoBean
    private ProductRepository productRepo;

    @Autowired
    private ObjectMapper objectMapper;


    @Test //test som kontroller hur ProductController hanterar en GET-förfrågan när man hämtar en produkt på dess ID
    public void testGetProductById() throws Exception{
        //Arrange
        Product product = new Product(1L, "computer", 200);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        //Act + Assert
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("computer"));
    }

    @Test //test som kontrollerar hur ProductController hanterar en POST-förfrågan för att skapa ny produkt
    public void testPostCreateProduct() throws Exception{
        //Arrange
        Product product = new Product(null, "computer", 200);
        when(productRepo.save(any(Product.class))).thenReturn(product);

        //Act + Assert
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("computer"))
                .andExpect(jsonPath("$.price").value(200));

    }


}
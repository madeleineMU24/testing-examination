package com.example.testexamination;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


//UNIT-TESTS


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductService productService;


    @Test //test för att kolla om en produkt finns, om inte så skapas den
    public void testIfProductExistAnCreateNewIfNot(){
        when(productRepo.existsByName("computer")).thenReturn(false);

        Product product = new Product(null, "computer", 200);

        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals("computer", result.getName());
        assertEquals(200, result.getPrice());
    }


    @Test //test för att kolla om det går att hämta en produkt utifrån id
    public void testGetProductById(){
        //Arrange
        Product product = new Product(1L, "computer", 200);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        //Act
        Product result = productService.getProductById(1L).orElse(null);

        //Assert
        assertEquals("computer", result.getName());
        verify(productRepo).findById(1L);
    }

    @Test //test om en produkt finns, så ska det komma en IllegalStateException
    public void shouldThrowIllegalStateExceptionIfProductExists(){
        //Arrange
        Product product = new Product(null, "computer", 200);

        when(productRepo.existsByName("computer")).thenReturn(true);

        //Assert (+Act)
        assertThrows(IllegalStateException.class, () -> productService.createProduct(product));
        verify(productRepo, never()).save(any());
    }
}
package com.example.testexamination;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testIfProductExistAnCreateNewIfNot(){
        when(productRepo.productExist("computer")).thenReturn(false);

        Product product = new Product(null, "computer", 200);

        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals("computer", result.getName());
        assertEquals(200, result.getPrice());
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfProductExists(){
        Product product = new Product(null, "computer", 200);

        when(productRepo.productExist("computer")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> productService.createProduct(product));

        verify(productRepo, never()).save(any());
    }
}
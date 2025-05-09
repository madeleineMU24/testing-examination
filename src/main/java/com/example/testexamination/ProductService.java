package com.example.testexamination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Product createProduct(Product product){
        if(productRepo.existsByName(product.getName())){
            throw new IllegalStateException("Product already exist");
        }
        return productRepo.save(product);
    }

    public void deleteProduct(Long id){
        productRepo.deleteById(id);
    }

    public Optional<Product> getProductById(Long id){
        return productRepo.findById(id);
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }
}

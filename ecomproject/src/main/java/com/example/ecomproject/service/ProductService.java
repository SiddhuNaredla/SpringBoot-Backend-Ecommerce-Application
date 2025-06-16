package com.example.ecomproject.service;

import com.example.ecomproject.dto.ProductDto;
import com.example.ecomproject.model.Category;
import com.example.ecomproject.model.Product;
import com.example.ecomproject.repository.CategoryRepository;
import com.example.ecomproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public String createProduct(ProductDto productDto, Category category) {
        Product product=new Product();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        productRepository.save(product);
        return "product created";
    }



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void updateProduct(int productID,ProductDto productDto, Category category) throws Exception {
        Optional<Product> optionalProduct=productRepository.findById(productID);
        if(!optionalProduct.isPresent()){
            throw new Exception("product not found");
        }
        Product product=optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        productRepository.save(product);
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto=new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setId(product.getId());
        return productDto;
    }
}

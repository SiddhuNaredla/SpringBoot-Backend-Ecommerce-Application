package com.example.ecomproject.controller;

import com.example.ecomproject.common.ApiResponse;
import com.example.ecomproject.dto.ProductDto;
import com.example.ecomproject.model.Category;
import com.example.ecomproject.model.Product;
import com.example.ecomproject.repository.CategoryRepository;
import com.example.ecomproject.repository.ProductRepository;
import com.example.ecomproject.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        final Optional<Category> optionalCategory=categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
            return new ResponseEntity<>(new ApiResponse(false,"category does not exists"),HttpStatus.BAD_REQUEST);
        String message=productService.createProduct(productDto,optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true,"product added"),HttpStatus.CREATED);
    }

    @GetMapping("/allproducts")
     public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
     }

     @PutMapping("/updateproduct/{productId}")
     public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") int productId,@RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory=categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"category not found"),HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productId,productDto,optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true,"updatedSucessfully"),HttpStatus.OK);
     }




}







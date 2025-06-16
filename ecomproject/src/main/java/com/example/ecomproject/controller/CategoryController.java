package com.example.ecomproject.controller;

import com.example.ecomproject.common.ApiResponse;
import com.example.ecomproject.model.Category;
import com.example.ecomproject.service.CategoryService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
        String message=categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true,message),HttpStatus.CREATED);

    }


    @GetMapping("/allcategories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable int categoryId, @Valid @RequestBody Category category){
       if(!categoryService.findById(categoryId))
           return new ResponseEntity<>(new ApiResponse(false,"category not found"),HttpStatus.NOT_FOUND);
       String message=categoryService.updateCategory(categoryId,category);
       return new ResponseEntity<>(new ApiResponse(true,message),HttpStatus.CREATED);
    }


    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId){
        if(!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new ApiResponse(false,"product not found"),HttpStatus.NOT_FOUND);
        }
        String message=categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse(true,message),HttpStatus.OK);
    }

}

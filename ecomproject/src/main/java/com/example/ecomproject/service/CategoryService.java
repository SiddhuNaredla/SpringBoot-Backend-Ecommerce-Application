package com.example.ecomproject.service;

import com.example.ecomproject.repository.CategoryRepository;
import com.example.ecomproject.model.Category;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public String createCategory(@Valid @RequestBody Category category){
        categoryRepository.save(category);
        return "created Sucessfully";
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public String updateCategory(int categoryId, @Valid Category updatedcategory) {
        Category category=categoryRepository.findById(categoryId).orElse(new Category());
        category.setCategoryName(updatedcategory.getCategoryName());
        category.setDescription(updatedcategory.getDescription());
        category.setImageUrl(updatedcategory.getImageUrl());
        categoryRepository.save(category);
        return "success";
    }

    public boolean findById(int categoryId) {
            if(categoryRepository.findById(categoryId).isPresent()) return true;
            return false;
    }

    public String deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
        return "deleted sucessfully";
    }
}

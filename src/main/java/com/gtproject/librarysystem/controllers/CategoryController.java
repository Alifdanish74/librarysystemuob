package com.gtproject.librarysystem.controllers;

import com.gtproject.librarysystem.models.Category;
import com.gtproject.librarysystem.models.CategoryRepo;
import com.gtproject.librarysystem.models.Student;
import com.gtproject.librarysystem.models.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo; // Replace with your actual service or repository

    // GET endpoint to retrieve all categories
    @GetMapping
    public Iterable<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    // POST endpoint to create a new category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryRepo.save(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions and potential rollbacks
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.gtproject.librarysystem.controllers;

import com.gtproject.librarysystem.models.Category;
import com.gtproject.librarysystem.repo.CategoryRepo;
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

    // Get a specific category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getStudentById(@PathVariable int id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        return optionalCategory.map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    // Delete a category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        Optional<Category> optionalStudent = categoryRepo.findById(id);
        if (optionalStudent.isPresent()) {
            categoryRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

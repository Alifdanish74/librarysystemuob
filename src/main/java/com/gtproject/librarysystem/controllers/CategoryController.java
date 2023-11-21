package com.gtproject.librarysystem.controllers;

import com.gtproject.librarysystem.models.Categories;
import com.gtproject.librarysystem.models.CategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoriesRepo categoryRepository;

    // Get all categories
    @GetMapping
    public Iterable<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get a specific category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable int id) {
        Optional<Categories> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new category
    @PostMapping
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        try {
            Categories savedCategory = categoryRepository.save(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


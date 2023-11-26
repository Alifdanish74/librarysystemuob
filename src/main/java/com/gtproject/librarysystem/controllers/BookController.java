package com.gtproject.librarysystem.controllers;
import com.gtproject.librarysystem.models.Book;
import com.gtproject.librarysystem.models.Category;
import com.gtproject.librarysystem.repo.BookRepo;
import com.gtproject.librarysystem.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookRepo bookRepo;
    @Autowired
    CategoryRepo categoryRepo;

    // Get all books
    @GetMapping
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        Iterable<Book> books = bookRepo.findAll();
        if (books.iterator().hasNext()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Optional<Book> optionalBook = bookRepo.findById(id);
        return optionalBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all books by category
    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<List<Book>> getAllBooksByCategory(@PathVariable String categoryName) {
        // Assuming you have a method in your repository to find books by category name
        List<Book> books = bookRepo.findByCategoryName(categoryName);

        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST endpoint to create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            // Retrieve the category based on category_id
            Category category = categoryRepo.findById(book.getCategory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category_id: " + book.getCategory().getId()));

            if(book.getAvailable_quantity() == 0) {
                book.setAvailable_quantity(book.getTotal_quantity());
            }
            // Set the category for the book
            book.setCategory(category);

            // Save the book
            Book savedBook = bookRepo.save(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}


package com.gtproject.librarysystem.controllers;

import com.gtproject.librarysystem.models.Book;
import com.gtproject.librarysystem.models.BookRepo;
import com.gtproject.librarysystem.models.Transaction;
import com.gtproject.librarysystem.models.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepo transRepo;
    @Autowired
    private BookRepo bookRepo;

    // Get all transactions
    @GetMapping
    public Iterable<Transaction> getAllTransactions() {
        return transRepo.findAll();


    }

    // Get a specific transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        Optional<Transaction> optionalTransaction = transRepo.findById(id);
        return optionalTransaction.map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            // Set the transaction date to the current date and time
            transaction.setTransaction_date(new Date());

            // Set the due date based on your business logic

            // Save the transaction
            Transaction savedTransaction = transRepo.save(transaction);

            // Update the available_quantity of the associated book
            Optional<Book> optionalBook = bookRepo.findById(transaction.getBook_id());
            optionalBook.ifPresent(book -> {
                if (book.getAvailable_quantity() > 0) {
                    // Log the book details and available quantity before the update
                    System.out.println("Before Update - Book ID: " + book.getId() + ", Available Quantity: " + book.getAvailable_quantity());

                    book.setAvailable_quantity(book.getAvailable_quantity() - 1);
                    bookRepo.save(book);

                    // Log the book details and available quantity after the update
                    System.out.println("After Update - Book ID: " + book.getId() + ", Available Quantity: " + book.getAvailable_quantity());
                }
            });

            // The transaction will be committed when this method completes successfully

            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions and potential rollbacks
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT endpoint to return a book
    @PutMapping("/{transactionId}/return")
    public ResponseEntity<Transaction> returnBook(@PathVariable int transactionId) {
        try {
            // Retrieve the transaction by ID
            Optional<Transaction> optionalTransaction = transRepo.findById(transactionId);
            if (optionalTransaction.isPresent()) {
                Transaction transaction = optionalTransaction.get();

                // Update the return_date to the current date and time
                transaction.setReturn_date(new Date());

                // Save the updated transaction
                Transaction updatedTransaction = transRepo.save(transaction);

                // Update the available_quantity of the associated book
                Optional<Book> optionalBook = bookRepo.findById(transaction.getBook_id());
                optionalBook.ifPresent(book -> {
                    // Log the book details and available quantity before the update
                    System.out.println("Before Update - Book ID: " + book.getId() + ", Available Quantity: " + book.getAvailable_quantity());

                    // Increase the available_quantity by 1
                    book.setAvailable_quantity(book.getAvailable_quantity() + 1);

                    // Save the updated book
                    bookRepo.save(book);

                    // Log the book details and available quantity after the update
                    System.out.println("After Update - Book ID: " + book.getId() + ", Available Quantity: " + book.getAvailable_quantity());
                });

                return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
            } else {
                // Transaction not found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions and potential rollbacks
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


package com.gtproject.librarysystem.repo;

import com.gtproject.librarysystem.models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepo extends CrudRepository<Transaction, Integer> {

    default Transaction getTransactionById( int id){
        Optional<Transaction> optionalTransaction = findById(id);
        return optionalTransaction.orElse(null);
    }
}

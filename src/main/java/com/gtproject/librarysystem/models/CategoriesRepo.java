package com.gtproject.librarysystem.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoriesRepo extends CrudRepository<Categories, Integer> {
    // You can add custom query methods if needed
}


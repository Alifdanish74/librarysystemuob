package com.gtproject.librarysystem.repo;

import com.gtproject.librarysystem.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StudentRepo extends CrudRepository<Student, Integer> {
    // You can add custom query methods if needed
}


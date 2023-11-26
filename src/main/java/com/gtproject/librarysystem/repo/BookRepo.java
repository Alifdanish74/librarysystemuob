package com.gtproject.librarysystem.repo;

import com.gtproject.librarysystem.models.Book;
import com.gtproject.librarysystem.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends CrudRepository <Book, Integer> {

    List<Book> findByCategoryName(String categoryName);

}


package com.lifelibrarians.lifebookshelf.book.repository;

import com.lifelibrarians.lifebookshelf.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

}

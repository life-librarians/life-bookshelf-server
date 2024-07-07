package com.lifelibrarians.lifebookshelf.book.repository;

import com.lifelibrarians.lifebookshelf.book.domain.BookContent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookContentRepository extends JpaRepository<BookContent, Long> {

}

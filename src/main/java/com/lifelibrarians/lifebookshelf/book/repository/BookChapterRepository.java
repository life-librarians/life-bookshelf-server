package com.lifelibrarians.lifebookshelf.book.repository;

import com.lifelibrarians.lifebookshelf.book.domain.BookChapter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookChapterRepository extends JpaRepository<BookChapter, Long> {

}

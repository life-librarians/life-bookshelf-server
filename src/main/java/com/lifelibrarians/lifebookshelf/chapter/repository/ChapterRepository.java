package com.lifelibrarians.lifebookshelf.chapter.repository;

import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}

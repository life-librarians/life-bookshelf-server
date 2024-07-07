package com.lifelibrarians.lifebookshelf.chapter.repository;

import com.lifelibrarians.lifebookshelf.chapter.domain.ChapterStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChapterStatusRepository extends JpaRepository<ChapterStatus, Long> {

}

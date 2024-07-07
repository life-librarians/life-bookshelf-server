package com.lifelibrarians.lifebookshelf.comment.repository;

import com.lifelibrarians.lifebookshelf.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

}

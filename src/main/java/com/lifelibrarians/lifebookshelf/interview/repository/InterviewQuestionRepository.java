package com.lifelibrarians.lifebookshelf.interview.repository;

import com.lifelibrarians.lifebookshelf.interview.domain.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, String> {

}

package com.lifelibrarians.lifebookshelf.interview.repository;

import com.lifelibrarians.lifebookshelf.interview.domain.InterviewQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, String> {

	@Query("SELECT iq FROM InterviewQuestion iq WHERE iq.interview.id = :interviewId")
	List<InterviewQuestion> findByInterviewId(Long interviewId);
}

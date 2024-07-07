package com.lifelibrarians.lifebookshelf.interview.repository;

import com.lifelibrarians.lifebookshelf.interview.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConversationRepository extends JpaRepository<Conversation, Long> {

}

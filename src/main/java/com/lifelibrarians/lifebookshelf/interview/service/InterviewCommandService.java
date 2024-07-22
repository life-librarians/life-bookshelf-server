package com.lifelibrarians.lifebookshelf.interview.service;

import com.lifelibrarians.lifebookshelf.interview.domain.Conversation;
import com.lifelibrarians.lifebookshelf.interview.domain.Interview;
import com.lifelibrarians.lifebookshelf.interview.dto.request.InterviewConversationCreateRequestDto;
import com.lifelibrarians.lifebookshelf.interview.repository.ConversationRepository;
import com.lifelibrarians.lifebookshelf.log.Logging;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Logging
public class InterviewCommandService {

	private final ConversationRepository conversationRepository;

	public void createConversations(Interview interview,
			InterviewConversationCreateRequestDto requestDto) {

		LocalDateTime now = LocalDateTime.now();
		List<Conversation> conversations = requestDto.getConversations().stream()
				.map(conversationDto -> Conversation.of(conversationDto.getContent(),
						conversationDto.getConversationType(), interview, now))
				.collect(Collectors.toList());

		conversationRepository.saveAll(conversations);
	}
}

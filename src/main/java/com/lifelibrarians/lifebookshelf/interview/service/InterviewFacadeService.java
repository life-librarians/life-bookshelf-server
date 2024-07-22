package com.lifelibrarians.lifebookshelf.interview.service;

import com.lifelibrarians.lifebookshelf.exception.status.InterviewExceptionStatus;
import com.lifelibrarians.lifebookshelf.interview.domain.Interview;
import com.lifelibrarians.lifebookshelf.interview.dto.request.InterviewConversationCreateRequestDto;
import com.lifelibrarians.lifebookshelf.log.Logging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Logging
public class InterviewFacadeService {

	private final InterviewQueryService interviewQueryService;
	private final InterviewCommandService interviewCommandService;


	/*-----------------------------------------READ-----------------------------------------*/


	/*-----------------------------------------CUD-----------------------------------------*/
	public void createConversations(Long memberId, Long interviewId,
			InterviewConversationCreateRequestDto requestDto) {
		Interview interview = interviewQueryService.getInterview(interviewId);
		if (!interview.getMember().getId().equals(memberId)) {
			throw InterviewExceptionStatus.INTERVIEW_NOT_OWNER.toServiceException();
		}
		interviewCommandService.createConversations(interview, requestDto);
	}

	public void updateCurrentQuestion(Long memberId, Long interviewId) {
		Interview interview = interviewQueryService.getInterview(interviewId);
		if (!interview.getMember().getId().equals(memberId)) {
			throw InterviewExceptionStatus.INTERVIEW_NOT_OWNER.toServiceException();
		}
		interviewCommandService.updateCurrentQuestion(interview);
	}
}

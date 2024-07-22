package com.lifelibrarians.lifebookshelf.interview.service;

import com.lifelibrarians.lifebookshelf.exception.status.InterviewExceptionStatus;
import com.lifelibrarians.lifebookshelf.interview.domain.Interview;
import com.lifelibrarians.lifebookshelf.interview.repository.InterviewRepository;
import com.lifelibrarians.lifebookshelf.log.Logging;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Logging
public class InterviewQueryService {

	private final InterviewRepository interviewRepository;

	public Interview getInterview(Long interviewId) {
		return interviewRepository.findById(
						interviewId)
				.orElseThrow(InterviewExceptionStatus.INTERVIEW_NOT_FOUND::toServiceException);
	}
}

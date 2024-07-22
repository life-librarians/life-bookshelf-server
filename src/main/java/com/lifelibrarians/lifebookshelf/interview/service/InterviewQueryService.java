package com.lifelibrarians.lifebookshelf.interview.service;

import com.lifelibrarians.lifebookshelf.exception.status.AuthExceptionStatus;
import com.lifelibrarians.lifebookshelf.exception.status.InterviewExceptionStatus;
import com.lifelibrarians.lifebookshelf.interview.domain.Interview;
import com.lifelibrarians.lifebookshelf.interview.domain.InterviewQuestion;
import com.lifelibrarians.lifebookshelf.interview.repository.InterviewQuestionRepository;
import com.lifelibrarians.lifebookshelf.interview.repository.InterviewRepository;
import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import com.lifelibrarians.lifebookshelf.member.repository.MemberRepository;
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
	private final InterviewQuestionRepository interviewQuestionRepository;
	private final MemberRepository memberRepository;

	public Interview getInterview(Long interviewId) {
		return interviewRepository.findById(interviewId)
				.orElseThrow(InterviewExceptionStatus.INTERVIEW_NOT_FOUND::toServiceException);
	}

	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(AuthExceptionStatus.MEMBER_NOT_FOUND::toServiceException);
	}

	public List<InterviewQuestion> getInterviewQuestions(Long interviewId) {
		return interviewQuestionRepository.findByInterviewId(interviewId);
	}
}

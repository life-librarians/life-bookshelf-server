package com.lifelibrarians.lifebookshelf.autobiography.service;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyUpdateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.repository.AutobiographyRepository;
import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.chapter.domain.ChapterStatus;
import com.lifelibrarians.lifebookshelf.chapter.repository.ChapterRepository;
import com.lifelibrarians.lifebookshelf.chapter.repository.ChapterStatusRepository;
import com.lifelibrarians.lifebookshelf.exception.status.AutobiographyExceptionStatus;
import com.lifelibrarians.lifebookshelf.interview.domain.InterviewQuestion;
import com.lifelibrarians.lifebookshelf.interview.repository.InterviewQuestionRepository;
import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
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
public class AutobiographyCommandService {

	private final ChapterRepository chapterRepository;
	private final ChapterStatusRepository chapterStatusRepository;
	private final AutobiographyRepository autobiographyRepository;
	private final InterviewQuestionRepository interviewQuestionRepository;

	public void createChapters(Member member, ChapterCreateRequestDto chapterCreateRequestDto) {

		List<Chapter> allByMemberId = chapterRepository.findAllByMemberId(member.getId());
		if (!allByMemberId.isEmpty()) {
			throw AutobiographyExceptionStatus.CHAPTER_ALREADY_EXISTS.toServiceException();
		}

		LocalDateTime now = LocalDateTime.now();
		List<Chapter> rootChapters = chapterCreateRequestDto.getChapters().stream()
				.map(chapterDto -> Chapter.of(chapterDto.getNumber(), chapterDto.getName(), now,
						null, member))
				.collect(Collectors.toList());

		chapterRepository.saveAll(rootChapters);

		List<Chapter> subChapters = chapterCreateRequestDto.getChapters().stream()
				.flatMap(chapterDto -> chapterDto.getSubchapters().stream()
						.map(subchapterDto -> {
							Long parentChapterId = rootChapters.stream()
									.filter(chapter -> chapter.getNumber()
											.equals(chapterDto.getNumber()))
									.findFirst()
									.orElseThrow(
											AutobiographyExceptionStatus.CHAPTER_NOT_FOUND::toServiceException)
									.getId();
							return Chapter.of(subchapterDto.getNumber(), subchapterDto.getName(),
									now,
									parentChapterId, member);
						})
				)
				.collect(Collectors.toList());

		chapterRepository.saveAll(subChapters);

		ChapterStatus chapterStatus = ChapterStatus.of(now, member, rootChapters.get(0));
		chapterStatusRepository.save(chapterStatus);
	}

	public void createAutobiography(Member member, AutobiographyCreateRequestDto requestDto,
			Chapter chapter) {
		LocalDateTime now = LocalDateTime.now();
		Autobiography autobiography = Autobiography.of(
				requestDto.getTitle(),
				requestDto.getContent(),
				// FIXME: 이미지 처리 필요
				requestDto.getPreSignedCoverImageUrl(),
				now,
				now,
				chapter,
				member
		);
		autobiographyRepository.save(autobiography);

		List<InterviewQuestion> interviewQuestions = requestDto.getInterviewQuestions().stream()
				.map(interviewDto -> InterviewQuestion.of(
						interviewDto.getOrder(),
						interviewDto.getQuestionText(),
						now
				))
				.collect(Collectors.toList());

		interviewQuestionRepository.saveAll(interviewQuestions);
	}

	public void patchAutobiography(Long memberId, Long autobiographyId,
			AutobiographyUpdateRequestDto requestDto) {
		Autobiography autobiography = autobiographyRepository.findById(autobiographyId)
				.orElseThrow(
						AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_FOUND::toServiceException);
		if (!autobiography.getMember().getId().equals(memberId)) {
			throw AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_OWNER.toServiceException();
		}
		autobiography.updateAutoBiography(requestDto.getTitle(), requestDto.getContent(),
				requestDto.getPreSignedCoverImageUrl(), LocalDateTime.now());
	}

	public void deleteAutobiography(Long memberId, Long autobiographyId) {
		Autobiography autobiography = autobiographyRepository.findById(autobiographyId)
				.orElseThrow(
						AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_FOUND::toServiceException);
		if (!autobiography.getMember().getId().equals(memberId)) {
			throw AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_OWNER.toServiceException();
		}
		autobiographyRepository.delete(autobiography);
	}
}

package com.lifelibrarians.lifebookshelf.autobiography.service;

import com.lifelibrarians.lifebookshelf.autobiography.dto.response.ChapterDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.ChapterListResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.SubchapterDto;
import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.chapter.domain.ChapterStatus;
import com.lifelibrarians.lifebookshelf.chapter.repository.ChapterRepository;
import com.lifelibrarians.lifebookshelf.chapter.repository.ChapterStatusRepository;
import com.lifelibrarians.lifebookshelf.exception.status.AuthExceptionStatus;
import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import com.lifelibrarians.lifebookshelf.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Logging
public class AutobiographyQueryService {

	private final MemberRepository memberRepository;
	private final ChapterRepository chapterRepository;
	private final ChapterStatusRepository chapterStatusRepository;

	public Member findMemberById(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(AuthExceptionStatus.MEMBER_NOT_FOUND::toServiceException);
	}

	public ChapterListResponseDto getChapters(Long memberId, Pageable pageable) {
		// Fetch paginated chapters with no parent (top-level chapters)
		Page<Chapter> chapterPage = chapterRepository.findByMemberIdAndParentChapterIdIsNull(
				memberId, pageable);

		// Get current chapter status
		Optional<ChapterStatus> chapterStatus = chapterStatusRepository.findByMemberId(memberId);

		// Map Chapters to ChapterDto
		List<ChapterDto> chapterDtos = chapterPage.getContent().stream().map(chapter -> {
			List<SubchapterDto> subchapterDtos = getSubchapterDtos(chapter.getId());
			return ChapterDto.builder()
					.chapterId(chapter.getId().intValue())
					.chapterNumber(chapter.getNumber())
					.chapterName(chapter.getName())
					.chapterCreatedAt(chapter.getCreatedAt())
					.subChapters(subchapterDtos)
					.build();
		}).collect(Collectors.toList());

		// Determine current chapter ID from status
		int currentChapterId = chapterStatus.map(
				status -> status.getCurrentChapter().getId().intValue()).orElse(0);

		// Create the response DTO
		return ChapterListResponseDto.builder()
				.currentChapterId(currentChapterId)
				.results(chapterDtos)
				.build();
	}

	private List<SubchapterDto> getSubchapterDtos(Long parentChapterId) {
		// Fetch subchapters using the parent chapter ID
		List<Chapter> subchapters = chapterRepository.findByParentChapterId(parentChapterId);
		return subchapters.stream().map(subchapter -> SubchapterDto.builder()
				.chapterId(subchapter.getId().intValue())
				.chapterNumber(subchapter.getNumber())
				.chapterName(subchapter.getName())
				.chapterCreatedAt(subchapter.getCreatedAt())
				.build()
		).collect(Collectors.toList());
	}
}

package com.lifelibrarians.lifebookshelf.autobiography.service;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterCreateRequestDto;
import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.chapter.domain.ChapterStatus;
import com.lifelibrarians.lifebookshelf.chapter.repository.ChapterRepository;
import com.lifelibrarians.lifebookshelf.chapter.repository.ChapterStatusRepository;
import com.lifelibrarians.lifebookshelf.exception.status.AutobiographyExceptionStatus;
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
}

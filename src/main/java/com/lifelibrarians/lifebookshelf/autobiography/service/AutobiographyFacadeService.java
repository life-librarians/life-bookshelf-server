package com.lifelibrarians.lifebookshelf.autobiography.service;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyUpdateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyDetailResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyListResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.ChapterListResponseDto;
import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.exception.status.AutobiographyExceptionStatus;
import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Logging
public class AutobiographyFacadeService {

	private final AutobiographyQueryService autobiographyQueryService;
	private final AutobiographyCommandService autobiographyCommandService;


	/*-----------------------------------------READ-----------------------------------------*/

	/*-----------------------------------------CUD-----------------------------------------*/

	public void createChapters(Long memberId, ChapterCreateRequestDto requestDto) {
		Member member = autobiographyQueryService.findMemberById(memberId);
		autobiographyCommandService.createChapters(member, requestDto);
	}

	public ChapterListResponseDto getChapters(Long memberId, Pageable pageable) {
		return autobiographyQueryService.getChapters(memberId, pageable);
	}

	public AutobiographyListResponseDto getAutobiographies(Long memberId) {
		return autobiographyQueryService.getAutobiographies(memberId);
	}

	public void createAutobiography(Long memberId, AutobiographyCreateRequestDto requestDto,
			Long chapterId) {
		Member member = autobiographyQueryService.findMemberById(memberId);
		if (autobiographyQueryService.isChapterHasAutobiography(
				chapterId)) {
			throw AutobiographyExceptionStatus.CHAPTER_ALREADY_HAS_AUTOBIOGRAPHY.toServiceException();
		}
		Chapter chapter = autobiographyQueryService.findChapterById(chapterId);
		autobiographyCommandService.createAutobiography(member, requestDto, chapter);
	}

	public AutobiographyDetailResponseDto getAutobiography(Long memberId, Long autobiographyId) {
		return autobiographyQueryService.getAutobiography(memberId, autobiographyId);
	}

	public void patchAutobiography(Long memberId, Long autobiographyId,
			AutobiographyUpdateRequestDto requestDto) {
		autobiographyCommandService.patchAutobiography(memberId, autobiographyId, requestDto);
	}
}

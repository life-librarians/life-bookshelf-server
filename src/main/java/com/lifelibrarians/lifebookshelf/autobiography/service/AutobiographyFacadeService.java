package com.lifelibrarians.lifebookshelf.autobiography.service;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.ChapterListResponseDto;
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
}

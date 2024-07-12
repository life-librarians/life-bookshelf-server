package com.lifelibrarians.lifebookshelf.autobiography.controller;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyUpdateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyDetailResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyListResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.ChapterListResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.exception.AutobiographyExceptionStatus;
import com.lifelibrarians.lifebookshelf.exception.annotation.ApiErrorCodeExample;
import com.lifelibrarians.lifebookshelf.interview.exception.InterviewExceptionStatus;
import com.lifelibrarians.lifebookshelf.log.Logging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/autobiographies")
@Tag(name = "자서전", description = "자서전 관련 API")
@Logging
public class AutobiographyController {

	@Operation(summary = "자서전 챕터 목록 생성", description = "자서전 챕터 목록을 생성합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "created"),
	})
	@ApiErrorCodeExample(
			autobiographyExceptionStatuses = {
					AutobiographyExceptionStatus.CHAPTER_ALREADY_EXISTS,
					AutobiographyExceptionStatus.CHAPTER_NAME_LENGTH_EXCEEDED,
			}
	)
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/chapters")
	@ResponseStatus(HttpStatus.CREATED)
	public void createChapters(
			@Valid @RequestBody ChapterCreateRequestDto requestDto
	) {

	}

	@Operation(summary = "자서전 챕터 목록 조회", description = "자서전 챕터 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/chapeters")
	public ChapterListResponseDto getChapters(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
	) {
		return ChapterListResponseDto.builder().build();
	}

	@Operation(summary = "특정 챕터의 자서전 목록 조회", description = "특정 챕터의 자서전 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@ApiErrorCodeExample(
			autobiographyExceptionStatuses = {
					AutobiographyExceptionStatus.CHAPTER_NOT_FOUND,
					AutobiographyExceptionStatus.CHAPTER_NOT_OWNER
			}
	)
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/chapters/{chapterId}")
	public AutobiographyListResponseDto getChapterAutobiographies(
			@PathVariable("chapterId") @Parameter(description = "챕터 ID") Long chapterId
	) {
		return AutobiographyListResponseDto.builder().build();
	}

	@Operation(summary = "자서전 생성 요청", description = "새 자서전을 생성합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "created"),
	})
	@ApiErrorCodeExample(
			autobiographyExceptionStatuses = {
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_TITLE_LENGTH_EXCEEDED,
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_CONTENT_LENGTH_EXCEEDED,
					AutobiographyExceptionStatus.CHAPTER_NOT_FOUND,
					AutobiographyExceptionStatus.CHAPTER_NOT_OWNER
			},
			interviewExceptionStatuses = {
					InterviewExceptionStatus.INTERVIEW_QUESTION_TEXT_LENGTH_EXCEEDED,
			}
	)
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/chapters/{chapterId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createAutobiography(
			@Valid @RequestBody AutobiographyCreateRequestDto requestDto,
			@PathVariable("chapterId") @Parameter(description = "챕터 ID") Long chapterId
	) {

	}

	@Operation(summary = "특정 자서전 상세 조회", description = "특정 자서전의 상세 정보를 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@ApiErrorCodeExample(
			autobiographyExceptionStatuses = {
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_FOUND,
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_OWNER
			}
	)
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{autobiographyId}")
	public AutobiographyDetailResponseDto getAutobiography(
			@PathVariable("autobiographyId") @Parameter(description = "자서전 ID") Long autobiographyId
	) {
		return AutobiographyDetailResponseDto.builder().build();
	}

	@Operation(summary = "자서전 수정 요청", description = "특정 자서전을 수정합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@ApiErrorCodeExample(
			autobiographyExceptionStatuses = {
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_FOUND,
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_OWNER,
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_TITLE_LENGTH_EXCEEDED,
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_CONTENT_LENGTH_EXCEEDED,
			}
	)
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/{autobiographyId}")
	public void updateAutobiography(
			@PathVariable("autobiographyId") @Parameter(description = "자서전 ID") Long autobiographyId,
			@Valid @RequestBody AutobiographyUpdateRequestDto requestDto
	) {

	}

	@Operation(summary = "자서전 삭제 요청", description = "특정 자서전을 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@ApiErrorCodeExample(
			autobiographyExceptionStatuses = {
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_FOUND,
					AutobiographyExceptionStatus.AUTOBIOGRAPHY_NOT_OWNER
			}
	)
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{autobiographyId}")
	public void deleteAutobiography(
			@PathVariable("autobiographyId") @Parameter(description = "자서전 ID") Long autobiographyId
	) {
		
	}
}

package com.lifelibrarians.lifebookshelf.autobiography.dto.response;

import com.lifelibrarians.lifebookshelf.autobiography.dto.ChapterDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "자서전 챕터 목록 응답")
@ToString
public class ChapterListResponseDto {

	@Schema(description = "현재 챕터 ID", example = "3")
	private final int currentChapterId;

	@ArraySchema(schema = @Schema(implementation = ChapterDto.class))
	private final List<ChapterDto> results;

	@Schema(description = "현재 페이지", example = "1")
	private final int currentPage;

	@Schema(description = "전체 요소 수", example = "4")
	private final int totalElements;

	@Schema(description = "전체 페이지 수", example = "1")
	private final int totalPages;

	@Schema(description = "다음 페이지 존재 여부", example = "false")
	private final boolean hasNextPage;

	@Schema(description = "이전 페이지 존재 여부", example = "false")
	private final boolean hasPreviousPage;
}
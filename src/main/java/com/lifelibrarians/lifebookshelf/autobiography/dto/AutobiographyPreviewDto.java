package com.lifelibrarians.lifebookshelf.autobiography.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "자서전 미리보기 정보")
@ToString
public class AutobiographyPreviewDto {

	@Schema(description = "자서전 ID", example = "1")
	private final int autobiographyId;

	@Schema(description = "제목", example = "My Early Life")
	private final String title;

	@Schema(description = "미리보기 내용", example = "This is the story of my early life...")
	private final String contentPreview;

	@Schema(description = "표지 이미지 URL", example = "https://example.com/image1.jpg")
	private final String coverImageUrl;

	@Schema(description = "생성일", example = "2023-01-01T00:00:00Z")
	private final String createdAt;

	@Schema(description = "수정일", example = "2023-01-02T00:00:00Z")
	private final String updatedAt;
}

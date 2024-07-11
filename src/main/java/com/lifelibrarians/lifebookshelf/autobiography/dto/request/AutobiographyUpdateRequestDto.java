package com.lifelibrarians.lifebookshelf.autobiography.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "자서전 수정 요청 DTO")
@ToString
public class AutobiographyUpdateRequestDto {

	@Schema(description = "자서전 제목", example = "Updated Autobiography Title")
	private final String title;

	@Schema(description = "자서전 내용", example = "Updated content of the autobiography.")
	private final String content;

	@Schema(description = "사전지정 커버 이미지 URL", example = "covers-images/random-string/image.png")
	private final String preSignedCoverImageUrl;
}

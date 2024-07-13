package com.lifelibrarians.lifebookshelf.autobiography.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "자서전 생성 요청 DTO")
@ToString
@FieldNameConstants
public class AutobiographyCreateRequestDto {

	@Schema(description = "자서전 제목", example = "My New Autobiography")
	private final String title;

	@Schema(description = "자서전 내용", example = "This is the content of my new autobiography.")
	private final String content;

	@Schema(description = "사전지정 커버 이미지 URL", example = "covers-images/random-string/image.png")
	private final String preSignedCoverImageUrl;

	@ArraySchema(schema = @Schema(implementation = InterviewQuestionRequestDto.class))
	private final List<InterviewQuestionRequestDto> interviewQuestions;
}

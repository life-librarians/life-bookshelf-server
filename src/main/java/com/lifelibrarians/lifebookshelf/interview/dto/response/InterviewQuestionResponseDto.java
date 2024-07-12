package com.lifelibrarians.lifebookshelf.interview.dto.response;


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
@Schema(description = "인터뷰 질문 목록 응답")
@ToString
public class InterviewQuestionResponseDto {

	@Schema(description = "현재 질문 ID", example = "1")
	private final Long currentQuestionId;

	@ArraySchema(schema = @Schema(implementation = InterviewQuestionDto.class))
	private final List<InterviewQuestionDto> results;
}

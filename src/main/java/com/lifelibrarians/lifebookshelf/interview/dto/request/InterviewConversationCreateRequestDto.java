package com.lifelibrarians.lifebookshelf.interview.dto.request;

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
@Schema(description = "인터뷰 대화 생성 요청 DTO")
@ToString
@FieldNameConstants
public class InterviewConversationCreateRequestDto {

	@ArraySchema(schema = @Schema(implementation = InterviewConversationDto.class))
	private final List<InterviewConversationDto> conversations;
}
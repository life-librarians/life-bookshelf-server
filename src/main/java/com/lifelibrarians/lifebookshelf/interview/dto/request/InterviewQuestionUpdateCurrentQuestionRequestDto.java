package com.lifelibrarians.lifebookshelf.interview.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "인터뷰 현재 질문 갱신 요청 DTO")
@ToString
public class InterviewQuestionUpdateCurrentQuestionRequestDto {

	@Schema(description = "다음 질문 ID", example = "1")
	private final Long nextQuestionId;
}

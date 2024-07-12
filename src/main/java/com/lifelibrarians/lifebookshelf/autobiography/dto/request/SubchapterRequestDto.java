package com.lifelibrarians.lifebookshelf.autobiography.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "자서전 서브챕터 정보")
@ToString
public class SubchapterRequestDto {

	@Schema(description = "서브챕터 번호", example = "1.1")
	private final String number;

	@Schema(description = "서브챕터 이름", example = "나의 첫번째 서브챕터")
	private final String name;
}
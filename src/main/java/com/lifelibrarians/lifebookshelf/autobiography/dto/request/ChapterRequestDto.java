package com.lifelibrarians.lifebookshelf.autobiography.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import lombok.experimental.FieldNameConstants;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "자서전 챕터 정보")
@ToString
@FieldNameConstants
public class ChapterRequestDto {

	@Schema(description = "챕터 번호", example = "1")
	private final String number;

	@Schema(description = "챕터 이름", example = "나의 첫번째 챕터")
	private final String name;

	@ArraySchema(schema = @Schema(implementation = SubchapterRequestDto.class))
	private final List<SubchapterRequestDto> subchapters;
}
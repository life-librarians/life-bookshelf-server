package com.lifelibrarians.lifebookshelf.autobiography.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "자서전 챕터 정보")
@ToString
public class ChapterDto {

	@Schema(description = "챕터 번호", example = "1")
	private final String number;

	@Schema(description = "챕터 이름", example = "나의 첫번째 챕터")
	private final String name;

	@ArraySchema(schema = @Schema(implementation = com.lifelibrarians.lifebookshelf.autobiography.dto.request.SubchapterDto.class))
	private final List<SubchapterDto> subchapters;
}
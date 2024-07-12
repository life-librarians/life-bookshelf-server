package com.lifelibrarians.lifebookshelf.autobiography.dto.request;

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
@Schema(description = "자서전 챕터 생성 요청")
@ToString
public class ChapterCreateRequestDto {

	@ArraySchema(schema = @Schema(implementation = ChapterRequestDto.class))
	private final List<ChapterRequestDto> chapters;
}
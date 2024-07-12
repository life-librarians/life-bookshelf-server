package com.lifelibrarians.lifebookshelf.autobiography.dto.response;

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
@Schema(description = "자서전 목록 정보")
@ToString
public class AutobiographyListResponseDto {

	@ArraySchema(schema = @Schema(implementation = AutobiographyPreviewDto.class))
	private final List<AutobiographyPreviewDto> results;
}

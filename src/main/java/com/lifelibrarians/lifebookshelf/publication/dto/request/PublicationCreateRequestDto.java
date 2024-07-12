package com.lifelibrarians.lifebookshelf.publication.dto.request;

import com.lifelibrarians.lifebookshelf.publication.domain.TitlePositionType;
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
@Schema(description = "출판 생성 요청 DTO")
@ToString
public class PublicationCreateRequestDto {

	@Schema(description = "제목 위치", example = "TOP")
	private final TitlePositionType titlePosition;

	@ArraySchema(schema = @Schema(description = "챕터 ID 목록", example = "[1, 2, 3, 4, 5]"))
	private final List<Long> chapterIds;
}

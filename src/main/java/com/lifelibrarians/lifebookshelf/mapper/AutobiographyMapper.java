package com.lifelibrarians.lifebookshelf.mapper;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyDetailResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AutobiographyMapper {

	@Mapping(source = "autobiography.id", target = "autobiographyId")
	@Mapping(source = "autobiography.content", target = "contentPreview", qualifiedByName = "truncate")
	AutobiographyPreviewDto toAutobiographyPreviewDto(Autobiography autobiography,
			Long chapterId);

	@Mapping(source = "autobiography.id", target = "autobiographyId")
	AutobiographyDetailResponseDto toAutobiographyDetailResponseDto(Autobiography autobiography);

	@Named("truncate")
	default String truncateContent(String content) {
		return content != null && content.length() > 16 ? content.substring(0, 16).concat("...")
				: content;
	}
}

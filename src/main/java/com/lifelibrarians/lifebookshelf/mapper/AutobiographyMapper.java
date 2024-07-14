package com.lifelibrarians.lifebookshelf.mapper;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutobiographyMapper {

	@Mapping(source = "autobiography.id", target = "autobiographyId")
	AutobiographyPreviewDto toAutobiographyPreviewDto(
			Autobiography autobiography,
			Long chapterId
	);
}

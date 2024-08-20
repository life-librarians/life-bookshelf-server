package com.lifelibrarians.lifebookshelf.mapper;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyDetailResponseDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.response.AutobiographyPreviewDto;
import com.lifelibrarians.lifebookshelf.image.service.ImageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AutobiographyMapper {

	@Autowired
	protected ImageService imageService;

	@Named("mapImageUrl")
	protected String mapImageUrl(String profileImageUrl) {
		return imageService.getImageUrl(profileImageUrl);
	}

	@Mapping(source = "autobiography.id", target = "autobiographyId")
	@Mapping(source = "autobiography.content", target = "contentPreview", qualifiedByName = "truncate")
	@Mapping(source = "autobiography.coverImageUrl", target = "coverImageUrl", qualifiedByName = "mapImageUrl")
	public abstract AutobiographyPreviewDto toAutobiographyPreviewDto(Autobiography autobiography,
			Long chapterId,
			Long interviewId
	);

	@Mapping(source = "autobiography.id", target = "autobiographyId")
	@Mapping(source = "autobiography.coverImageUrl", target = "coverImageUrl", qualifiedByName = "mapImageUrl")
	public abstract AutobiographyDetailResponseDto toAutobiographyDetailResponseDto(
			Autobiography autobiography,
			Long interviewId
	);

	@Named("truncate")
	String truncateContent(String content) {
		return content != null && content.length() > 16 ? content.substring(0, 16).concat("...")
				: content;
	}
}

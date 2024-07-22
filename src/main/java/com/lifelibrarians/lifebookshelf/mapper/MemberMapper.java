package com.lifelibrarians.lifebookshelf.mapper;

import com.lifelibrarians.lifebookshelf.image.service.ImageService;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import com.lifelibrarians.lifebookshelf.member.dto.response.MemberProfileResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class MemberMapper {

	@Autowired
	protected ImageService imageService;

	@Named("mapImageUrl")
	protected String mapImageUrl(String profileImageUrl) {
		return imageService.getImageUrl(profileImageUrl);
	}

	@Mapping(source = "member.id", target = "memberId")
	@Mapping(source = "member.profileImageUrl", target = "profileImageUrl", qualifiedByName = "mapImageUrl")
	public abstract MemberProfileResponseDto toMemberProfileResponseDto(Member member);
}

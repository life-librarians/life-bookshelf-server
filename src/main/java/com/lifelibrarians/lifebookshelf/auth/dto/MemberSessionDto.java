package com.lifelibrarians.lifebookshelf.auth.dto;

import com.lifelibrarians.lifebookshelf.member.domain.MemberRole;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor
@Builder
@ToString
public class MemberSessionDto {

	private final Long userId;
	private final List<MemberRole> roles;
}

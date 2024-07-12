package com.lifelibrarians.lifebookshelf.member.dto.request;

import com.lifelibrarians.lifebookshelf.member.domain.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "회원 정보 수정 요청 DTO")
@ToString
public class MemberUpdateRequestDto {

	@Schema(description = "이름", example = "John Doe")
	private final String name;

	@Schema(description = "생년월일", example = "2000-01-01")
	private final String bornedAt;

	@Schema(description = "성별", example = "MALE")
	private final GenderType gender;

	@Schema(description = "자녀 여부", example = "true")
	private final boolean hasChildren;
}

package com.lifelibrarians.lifebookshelf.member.service;

import com.lifelibrarians.lifebookshelf.exception.status.AuthExceptionStatus;
import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import com.lifelibrarians.lifebookshelf.member.domain.MemberMetadata;
import com.lifelibrarians.lifebookshelf.member.dto.request.MemberUpdateRequestDto;
import com.lifelibrarians.lifebookshelf.member.repository.MemberMetadataRepository;
import com.lifelibrarians.lifebookshelf.member.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Logging
public class MemberCommandService {

	private final MemberRepository memberRepository;
	private final MemberMetadataRepository memberMetadataRepository;


	public void updateMember(Long memberId, MemberUpdateRequestDto requestDto) {

		LocalDateTime now = LocalDateTime.now();

		Member member = memberRepository.findByIdWithMetadata(memberId)
				.orElseThrow(AuthExceptionStatus.MEMBER_NOT_FOUND::toServiceException);

		if (member.getMemberMemberMetadata() == null) {
			MemberMetadata memberMetadata = MemberMetadata.of(
					requestDto.getName(),
					requestDto.getBornedAt(),
					requestDto.getGender(),
					requestDto.isHasChildren(),
					now,
					now,
					member
			);

			memberMetadata = memberMetadataRepository.save(
					memberMetadata); // Save the MemberMetadata first
			member.setMemberMemberMetadata(memberMetadata);
		} else {
			member.getMemberMemberMetadata().update(
					requestDto.getName(),
					requestDto.getBornedAt(),
					requestDto.getGender(),
					requestDto.isHasChildren()
			);
		}
	}
}

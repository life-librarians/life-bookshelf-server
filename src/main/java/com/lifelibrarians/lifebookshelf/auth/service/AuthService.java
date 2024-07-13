package com.lifelibrarians.lifebookshelf.auth.service;

import com.lifelibrarians.lifebookshelf.auth.dto.EmailLoginRequestDto;
import com.lifelibrarians.lifebookshelf.auth.dto.JwtLoginTokenDto;
import com.lifelibrarians.lifebookshelf.auth.exception.AuthExceptionStatus;
import com.lifelibrarians.lifebookshelf.auth.jwt.JwtTokenProvider;
import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.member.domain.LoginType;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import com.lifelibrarians.lifebookshelf.member.domain.MemberRole;
import com.lifelibrarians.lifebookshelf.member.domain.PasswordMember;
import com.lifelibrarians.lifebookshelf.member.repository.MemberRepository;
import com.lifelibrarians.lifebookshelf.member.repository.PasswordMemberRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Logging
@Transactional
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final PasswordMemberRepository passwordMemberRepository;

	// FIXME: 데모용 로그인을 위해, 실제 로그인 로직을 적용하지 않았음.
	// FIXME: 아이디와 비밀번호만 넣으면 별도의 인증 없이 바로 회원가입 & 로그인이 진행되도록 구현했음.
	// FIXME: 추후 실제 로그인 로직을 적용해야 함.
	public JwtLoginTokenDto loginEmail(EmailLoginRequestDto requestDto) {
//		1. 이메일로 회원 조회
		Optional<Member> member = memberRepository.findByEmail(requestDto.getEmail());

//		2. 회원이 없으면 회원가입
		LocalDateTime now = LocalDateTime.now();
		if (member.isEmpty()) {
			PasswordMember passwordMember = PasswordMember.of(
					requestDto.getPassword()
			);
			passwordMemberRepository.save(passwordMember);
			Member newMember = Member.of(
					LoginType.PASSWORD,
					requestDto.getEmail(),
					MemberRole.MEMBER,
					null,
					UUID.randomUUID().toString(), // FIXME: 데모용 랜덤 닉네임
					now,
					now,
					null
			);
			newMember.addPasswordMember(passwordMember);
			memberRepository.save(newMember);
			member = Optional.of(newMember);
		} else {
//			이미 탈퇴한 회원인 경우
			if (member.get().getDeletedAt() != null) {
				throw AuthExceptionStatus.MEMBER_ALREADY_WITHDRAWN.toServiceException();
			}
//			회원이 있으면 비밀번호 확인
			PasswordMember passwordMember = member.get().getPasswordMember();
			if (!passwordMember.matchPassword(requestDto.getPassword())) {
				throw AuthExceptionStatus.EMAIL_OR_PASSWORD_INCORRECT.toServiceException();
			}
		}

//		3. JWT 토큰 생성
		Jwt jwt = jwtTokenProvider.createMemberAccessToken(member.get().getId());
		return JwtLoginTokenDto.builder()
				.accessToken(jwt.getTokenValue())
				.build();
	}

	public void unregister(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(AuthExceptionStatus.MEMBER_NOT_FOUND::toServiceException);
//		TODO: Soft Delete를 적용하여, 추후 영구 삭제 스케줄러를 적용할 수 있도록 함.
//		LocalDateTime now = LocalDateTime.now();
//		member.softDelete(now);
//		memberRepository.save(member);
		memberRepository.delete(member);
	}
}

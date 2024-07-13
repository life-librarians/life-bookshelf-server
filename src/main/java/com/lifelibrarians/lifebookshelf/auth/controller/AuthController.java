package com.lifelibrarians.lifebookshelf.auth.controller;

import com.lifelibrarians.lifebookshelf.auth.dto.JwtLoginTokenDto;
import com.lifelibrarians.lifebookshelf.auth.dto.RegisterRequestDto;
import com.lifelibrarians.lifebookshelf.auth.exception.AuthExceptionStatus;
import com.lifelibrarians.lifebookshelf.auth.password.annotation.OneWayEncryption;
import com.lifelibrarians.lifebookshelf.auth.password.annotation.TargetMapping;
import com.lifelibrarians.lifebookshelf.exception.annotation.ApiErrorCodeExample;
import com.lifelibrarians.lifebookshelf.log.Logging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "인증", description = "인증 관련 API")
@Logging
public class AuthController {

	@Operation(summary = "이메일 회원가입", description = "이메일 회원가입을 요청합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공",
					content = @Content(schema = @Schema(implementation = JwtLoginTokenDto.class))
			),
	})
	@ApiErrorCodeExample(
			authExceptionStatuses = {
					AuthExceptionStatus.INVALID_EMAIL_FORMAT,
					AuthExceptionStatus.EMAIL_TOO_LONG,
					AuthExceptionStatus.PASSWORD_FORMAT_ERROR,
					AuthExceptionStatus.MEMBER_ALREADY_EXISTS,
			}
	)
	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@OneWayEncryption({
			@TargetMapping(clazz = RegisterRequestDto.class, fields = {
					RegisterRequestDto.Fields.password})
	})
	public void register(
			@Valid @ModelAttribute RegisterRequestDto requestDto
	) {
	}
}

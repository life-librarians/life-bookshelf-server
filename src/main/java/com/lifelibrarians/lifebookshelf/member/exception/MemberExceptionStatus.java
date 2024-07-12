package com.lifelibrarians.lifebookshelf.member.exception;

import com.lifelibrarians.lifebookshelf.exception.status.ErrorReason;
import com.lifelibrarians.lifebookshelf.exception.status.ExceptionStatus;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum MemberExceptionStatus implements ExceptionStatus {

	MEMBER_NAME_LENGTH_EXCEEDED(400, "MEMBER001", "이름은 최대 64자까지 입력할 수 있습니다.");

	private final int statusCode;
	private final String code;
	private final String message;

	@Override
	public ErrorReason getErrorReason() {
		return ErrorReason.builder()
				.statusCode(statusCode)
				.code(code)
				.message(message)
				.build();
	}
}

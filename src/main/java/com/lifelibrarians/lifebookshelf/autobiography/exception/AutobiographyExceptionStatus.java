package com.lifelibrarians.lifebookshelf.autobiography.exception;

import com.lifelibrarians.lifebookshelf.exception.status.ErrorReason;
import com.lifelibrarians.lifebookshelf.exception.status.ExceptionStatus;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum AutobiographyExceptionStatus implements ExceptionStatus {

	CHAPTER_ALREADY_EXISTS(409, "BIO001", "이미 존재하는 챕터입니다."),
	CHAPTER_NAME_LENGTH_EXCEEDED(400, "BIO002", "챕터 이름은 최대 64자까지 입력할 수 있습니다."),
	CHAPTER_NOT_FOUND(404, "BIO003", "챕터 ID가 존재하지 않습니다."),
	CHAPTER_NOT_OWNER(403, "BIO004", "해당 챕터의 주인이 아닙니다."),
	AUTOBIOGRAPHY_TITLE_LENGTH_EXCEEDED(400, "BIO005", "자서전 제목은 최대 64자까지 입력할 수 있습니다."),
	AUTOBIOGRAPHY_CONTENT_LENGTH_EXCEEDED(400, "BIO006", "자서전 내용은 최대 30000자까지 입력할 수 있습니다."),
	AUTOBIOGRAPHY_NOT_FOUND(404, "BIO008", "자서전 ID가 존재하지 않습니다."),
	AUTOBIOGRAPHY_NOT_OWNER(403, "BIO009", "해당 자서전의 주인이 아닙니다.");

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

package com.lifelibrarians.lifebookshelf.publication.exception;

import com.lifelibrarians.lifebookshelf.exception.status.ErrorReason;
import com.lifelibrarians.lifebookshelf.exception.status.ExceptionStatus;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum PublicationExceptionStatus implements ExceptionStatus {

	PUBLICATION_NOT_FOUND(404, "PUB001", "존재하지 않는 출판 ID입니다."),
	PUBLICATION_NOT_OWNER(403, "PUB002", "해당 출판에 대한 권한이 없습니다.");

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

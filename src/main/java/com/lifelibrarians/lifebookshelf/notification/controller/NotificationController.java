package com.lifelibrarians.lifebookshelf.notification.controller;

import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.notification.dto.request.NotificationHistoryDeleteRequestDto;
import com.lifelibrarians.lifebookshelf.notification.dto.request.NotificationHistoryReadRequestDto;
import com.lifelibrarians.lifebookshelf.notification.dto.request.SubscribingNotificationUpdateRequestDto;
import com.lifelibrarians.lifebookshelf.notification.dto.response.NotificationHistoryListResponseDto;
import com.lifelibrarians.lifebookshelf.notification.dto.response.SubscribingNotificationListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
@Tag(name = "알림", description = "알림 관련 API")
@Logging
public class NotificationController {

	@Operation(summary = "구독중인 알림 목록 조회", description = "구독중인 알림 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/subscriptions")
	public SubscribingNotificationListResponseDto getSubscriptions() {
		return SubscribingNotificationListResponseDto.builder().build();
	}

	@Operation(summary = "알림 구독 변경", description = "알림 구독을 변경합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/subscriptions")
	public SubscribingNotificationListResponseDto updateSubscriptions(
			@Valid @RequestBody SubscribingNotificationUpdateRequestDto requestDto
	) {
		return SubscribingNotificationListResponseDto.builder().build();
	}

	@Operation(summary = "알림 내역 목록 조회", description = "알림 내역 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/histories")
	public NotificationHistoryListResponseDto getHistories(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
	) {
		return NotificationHistoryListResponseDto.builder().build();
	}

	@Operation(summary = "알림 내역 읽음 처리", description = "알림 내역을 읽음 처리합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/histories")
	public NotificationHistoryListResponseDto updateHistories(
			@Valid @RequestBody NotificationHistoryReadRequestDto requestDto
	) {
		return NotificationHistoryListResponseDto.builder().build();
	}

	@Operation(summary = "알림 내역 삭제", description = "알림 내역을 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/histories")
	public void deleteHistories(
			@Valid @RequestBody NotificationHistoryDeleteRequestDto requestDto
	) {
	}
}
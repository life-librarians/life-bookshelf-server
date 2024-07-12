package com.lifelibrarians.lifebookshelf.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "구독중인 알림 수정 요청")
@ToString
public class SubscribingNotificationUpdateRequestDto {

	@Schema(description = "변경할 알림 ID 목록", example = "[1, 3, 5]")
	private final List<Long> notificationIds;
}

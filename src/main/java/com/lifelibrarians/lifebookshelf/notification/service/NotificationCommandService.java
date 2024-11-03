package com.lifelibrarians.lifebookshelf.notification.service;

import com.lifelibrarians.lifebookshelf.log.Logging;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import com.lifelibrarians.lifebookshelf.notification.domain.DeviceRegistry;
import com.lifelibrarians.lifebookshelf.notification.repository.DeviceRegistryRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Logging
public class NotificationCommandService {

	private final DeviceRegistryRepository deviceRegistryRepository;

	public void updateDeviceToken(Member member, String deviceToken, LocalDateTime now) {
		List<DeviceRegistry> registries = deviceRegistryRepository
				.findByMemberIdOrderByCreatedAt(member.getId());
		if (registries.stream().anyMatch(r -> r.getToken().equals(deviceToken))) {
			return;
		}
		for (DeviceRegistry registry : registries) {
			if (registry.getExpiredAt().isBefore(now)) {
				deviceRegistryRepository.delete(registry);
			}
		}
		LocalDateTime expiredAt = now.plusDays(DeviceRegistry.DEFAULT_EXPIRED_DAYS);
		DeviceRegistry registry = DeviceRegistry.of(member, deviceToken, now, expiredAt);
		if (registries.size() >= DeviceRegistry.MAX_DEVICE_COUNT) {
			DeviceRegistry oldest = registries.get(0);
			deviceRegistryRepository.delete(oldest);
		}
		deviceRegistryRepository.save(registry);
	}
}

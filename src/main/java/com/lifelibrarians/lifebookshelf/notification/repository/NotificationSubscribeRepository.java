package com.lifelibrarians.lifebookshelf.notification.repository;

import com.lifelibrarians.lifebookshelf.notification.domain.NotificationSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationSubscribeRepository extends
		JpaRepository<NotificationSubscribe, Long> {

}

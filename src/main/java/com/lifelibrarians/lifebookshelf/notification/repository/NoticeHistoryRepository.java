package com.lifelibrarians.lifebookshelf.notification.repository;

import com.lifelibrarians.lifebookshelf.notification.domain.NoticeHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeHistoryRepository extends JpaRepository<NoticeHistory, Long> {

}

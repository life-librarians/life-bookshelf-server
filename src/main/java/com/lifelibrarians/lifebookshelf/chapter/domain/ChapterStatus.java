package com.lifelibrarians.lifebookshelf.chapter.domain;

import com.lifelibrarians.lifebookshelf.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "ChapterStatuses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ChapterStatus {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_chapter_id", nullable = false)
	private Chapter currentChapter;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;

	@LastModifiedDate
	@Column(nullable = false)
	private OffsetDateTime lastUpdated;

}

package com.lifelibrarians.lifebookshelf.chapter.domain;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "chapters")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chapter {

	/* 고유 정보 { */
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer number;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDateTime createdAt;
	/* } 고유 정보 */

	/* 연관 정보 { */
	@OneToMany(mappedBy = "chapter")
	private Set<Autobiography> chapterAutobiographies;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@OneToMany(mappedBy = "currentChapter")
	private Set<ChapterStatus> currentChapterChapterStatuses;
	/* } 연관 정보 */

	/* 생성자 { */
	protected Chapter(Integer number, String name, LocalDateTime createdAt) {
		this.number = number;
		this.name = name;
		this.createdAt = createdAt;
	}

	public static Chapter of(Integer number, String name, LocalDateTime createdAt) {
		return new Chapter(number, name, createdAt);
	}
	/* } 생성자 */
}

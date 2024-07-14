package com.lifelibrarians.lifebookshelf.chapter.domain;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

	@Column
	private Long parentChapterId;

	@Column(nullable = false)
	@Setter
	private String number;

	@Column(nullable = false)
	@Setter
	private String name;

	@Column(nullable = false)
	@Setter
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
	protected Chapter(String number, String name, LocalDateTime createdAt, Long parentChapterId,
			Member member) {
		this.number = number;
		this.name = name;
		this.createdAt = createdAt;
		this.parentChapterId = parentChapterId;
		this.member = member;
	}

	public static Chapter of(String number, String name, LocalDateTime createdAt,
			Long parentChapterId, Member member) {
		return new Chapter(number, name, createdAt, parentChapterId, member);
	}
	/* } 생성자 */
}

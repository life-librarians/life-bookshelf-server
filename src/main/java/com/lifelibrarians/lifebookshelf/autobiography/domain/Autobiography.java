package com.lifelibrarians.lifebookshelf.autobiography.domain;

import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.interview.domain.Interview;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "autobiographies")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Autobiography {

	/* 고유 정보 { */
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column(columnDefinition = "longtext")
	private String content;

	@Column
	private String coverImageUrl;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
	/* } 고유 정보 */

	/* 연관 정보 { */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chapter_id", nullable = false)
	private Chapter chapter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@OneToMany(mappedBy = "autobiography")
	private Set<Interview> autobiographyInterviews;

	@OneToMany(mappedBy = "chapter")
	private Set<Interview> chapterInterviews;

	@OneToMany(mappedBy = "member")
	private Set<Interview> memberInterviews;
	/* } 연관 정보 */

	/* 생성자 { */
	protected Autobiography(String title, String content, String coverImageUrl,
			LocalDateTime createdAt, LocalDateTime updatedAt, Chapter chapter, Member member) {
		this.title = title;
		this.content = content;
		this.coverImageUrl = coverImageUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.chapter = chapter;
		this.member = member;
	}

	public static Autobiography of(String title, String content, String coverImageUrl,
			LocalDateTime createdAt, LocalDateTime updatedAt, Chapter chapter, Member member) {
		return new Autobiography(title, content, coverImageUrl, createdAt, updatedAt, chapter,
				member);
	}
	/* } 생성자 */
}

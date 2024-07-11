package com.lifelibrarians.lifebookshelf.interview.domain;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "interviews")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview {

	/* 고유 정보 { */
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime createdAt;
	/* } 고유 정보 */

	/* 연관 정보 { */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autobiography_id", nullable = false)
	private Autobiography autobiography;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chapter_id", nullable = false)
	private Autobiography chapter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Autobiography member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_question_id", nullable = false)
	private InterviewQuestion currentQuestion;

	@OneToMany(mappedBy = "interview")
	private Set<Conversation> interviewConversations;
	/* } 연관 정보 */

	/* 생성자 { */
	protected Interview(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public static Interview of(LocalDateTime createdAt) {
		return new Interview(createdAt);
	}
	/* } 생성자 */
}

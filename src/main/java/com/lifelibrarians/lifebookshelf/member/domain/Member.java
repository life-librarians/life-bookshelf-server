package com.lifelibrarians.lifebookshelf.member.domain;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import com.lifelibrarians.lifebookshelf.book.domain.Book;
import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.chapter.domain.ChapterStatus;
import com.lifelibrarians.lifebookshelf.comment.domain.Comment;
import com.lifelibrarians.lifebookshelf.like.Like;
import com.lifelibrarians.lifebookshelf.notification.domain.NoticeHistory;
import com.lifelibrarians.lifebookshelf.notification.domain.NotificationSubscribe;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "members")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	/* 고유 정보 { */
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LoginType loginType;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false, name = "\"role\"")
	private MemberRole role;

	@Column
	private String profileImageUrl;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime nicknameUpdatedAt;

	@Column
	private LocalDateTime deletedAt;
	/* } 고유 정보 */

	/* 연관 정보 { */
	@OneToMany(mappedBy = "member")
	private Set<NotificationSubscribe> memberNotificationSubscribes;

	@OneToMany(mappedBy = "member")
	private Set<Autobiography> memberAutobiographies;

	@OneToMany(mappedBy = "member")
	private Set<Book> memberBooks;

	@OneToMany(mappedBy = "member")
	private Set<Comment> memberComments;

	@OneToMany(mappedBy = "member")
	private Set<Like> memberLikes;

	@OneToMany(mappedBy = "member")
	private Set<Chapter> memberChapters;

	@OneToMany(mappedBy = "member")
	private Set<MemberMetadata> memberMemberMetadatas;

	@OneToMany(mappedBy = "member")
	private Set<NoticeHistory> memberNoticeHistories;

	@OneToMany(mappedBy = "member")
	private Set<ChapterStatus> memberChapterStatuses;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "social_member_id", unique = true)
	private SocialMember socialMember;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "password_member_id", unique = true)
	private PasswordMember passwordMember;
	/* } 연관 정보 */

	/* 생성자 { */
	protected Member(LoginType loginType, String email, MemberRole role, String profileImageUrl,
			String nickname, LocalDateTime createdAt, LocalDateTime nicknameUpdatedAt,
			LocalDateTime deletedAt) {
		this.loginType = loginType;
		this.email = email;
		this.role = role;
		this.profileImageUrl = profileImageUrl;
		this.nickname = nickname;
		this.createdAt = createdAt;
		this.nicknameUpdatedAt = nicknameUpdatedAt;
		this.deletedAt = deletedAt;
	}

	public static Member of(LoginType loginType, String email, MemberRole role,
			String profileImageUrl,
			String nickname, LocalDateTime createdAt, LocalDateTime nicknameUpdatedAt,
			LocalDateTime deletedAt) {
		return new Member(loginType, email, role, profileImageUrl, nickname, createdAt,
				nicknameUpdatedAt, deletedAt);
	}
	/* } 생성자 */
}

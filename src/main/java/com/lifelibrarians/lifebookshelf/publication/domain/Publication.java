package com.lifelibrarians.lifebookshelf.publication.domain;

import com.lifelibrarians.lifebookshelf.book.domain.Book;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Publications")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Publication {

	/* 고유 정보 { */
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private TitlePosition titlePosition;

	@Column(nullable = false)
	private PublishStatus publishStatus;

	@Column(nullable = false)
	private LocalDateTime requestedAt;

	@Column(nullable = false)
	private LocalDateTime willPublishedAt;

	@Column
	private LocalDateTime publishedAt;

	@Column
	private LocalDateTime deletedAt;
	/* } 고유 정보 */

	/* 연관 정보 { */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;
	/* } 연관 정보 */

	/* 생성자 { */
	protected Publication(Integer price, TitlePosition titlePosition, PublishStatus publishStatus,
			LocalDateTime requestedAt, LocalDateTime willPublishedAt, LocalDateTime publishedAt,
			LocalDateTime deletedAt) {
		this.price = price;
		this.titlePosition = titlePosition;
		this.publishStatus = publishStatus;
		this.requestedAt = requestedAt;
		this.willPublishedAt = willPublishedAt;
		this.publishedAt = publishedAt;
		this.deletedAt = deletedAt;
	}

	public static Publication of(Integer price, TitlePosition titlePosition,
			PublishStatus publishStatus,
			LocalDateTime requestedAt, LocalDateTime willPublishedAt, LocalDateTime publishedAt,
			LocalDateTime deletedAt) {
		return new Publication(price, titlePosition, publishStatus, requestedAt, willPublishedAt,
				publishedAt, deletedAt);
	}
	/* } 생성자 */
}

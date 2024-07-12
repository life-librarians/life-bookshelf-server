package com.lifelibrarians.lifebookshelf.community.book.domain;

import javax.persistence.*;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "bookChapters")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookChapter {

	/* 고유 정보 { */
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer number;

	@Column(nullable = false)
	private String name;
	/* } 고유 정보 */

	/* 연관 정보 { */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@OneToMany(mappedBy = "bookChapter")
	private Set<BookContent> bookChapterBookContents;
	/* } 연관 정보 */

	/* 생성자 { */
	protected BookChapter(Integer number, String name) {
		this.number = number;
		this.name = name;
	}

	public static BookChapter of(Integer number, String name) {
		return new BookChapter(number, name);
	}
	/* } 생성자 */
}

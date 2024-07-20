package utils.testdouble.chapter;

import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.chapter.domain.ChapterStatus;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import utils.testdouble.TestEntity;

@Builder
public class TestChapterStatus implements TestEntity<ChapterStatus, Long> {

	public static final LocalDateTime DEFAULT_TIME = LocalDateTime.of(LocalDate.EPOCH,
			LocalTime.MIDNIGHT);


	@Builder.Default
	private LocalDateTime updatedAt = DEFAULT_TIME;
	@Builder.Default
	private Member member = null;
	@Builder.Default
	private Chapter currentChapter = null;

	public static ChapterStatus asDefaultEntity() {
		return TestChapterStatus.builder().build().asEntity();
	}

	@Override
	public ChapterStatus asEntity() {
		return ChapterStatus.of(
				this.updatedAt,
				this.member,
				this.currentChapter
		);
	}

	@Override
	public ChapterStatus asMockEntity(Long aLong) {
		return null;
	}
}

package utils.testdouble.chapter;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.SubchapterRequestDto;
import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import com.lifelibrarians.lifebookshelf.member.domain.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import utils.testdouble.TestEntity;

@Builder
public class TestChapter implements TestEntity<Chapter, Long> {

	public static final LocalDateTime DEFAULT_TIME = LocalDateTime.of(LocalDate.EPOCH,
			LocalTime.MIDNIGHT);

	@Builder.Default
	private Long parentChapterId = null;
	@Builder.Default
	private String number = null;
	@Builder.Default
	private String name = null;
	@Builder.Default
	private LocalDateTime createdAt = DEFAULT_TIME;
	@Builder.Default
	private Member member = null;

	public static Chapter asDefaultEntity() {
		return TestChapter.builder().build().asEntity();
	}

	@Override
	public Chapter asEntity() {
		return Chapter.of(
				this.number,
				this.name,
				this.createdAt,
				this.parentChapterId,
				this.member
		);
	}

	@Override
	public Chapter asMockEntity(Long aLong) {
		return null;
	}
}

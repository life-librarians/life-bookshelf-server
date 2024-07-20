package utils.testdouble.interview;

import com.lifelibrarians.lifebookshelf.interview.domain.Interview;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import utils.testdouble.TestEntity;

@Builder
public class TestInterview implements TestEntity<Interview, Long> {

	public static final LocalDateTime DEFAULT_TIME = LocalDateTime.of(LocalDate.EPOCH,
			LocalTime.MIDNIGHT);

	@Builder.Default
	private LocalDateTime createdAt = DEFAULT_TIME;

	public static Interview asDefaultEntity() {
		return TestInterview.builder().build().asEntity();
	}

	@Override
	public Interview asEntity() {
		return Interview.of(
				this.createdAt
		);
	}

	@Override
	public Interview asMockEntity(Long aLong) {
		return null;
	}
}

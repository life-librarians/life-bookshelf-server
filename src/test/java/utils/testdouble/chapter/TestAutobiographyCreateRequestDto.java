package utils.testdouble.chapter;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.InterviewQuestionRequestDto;
import java.util.List;
import utils.testdouble.autobiography.TestAutobiography;

public class TestAutobiographyCreateRequestDto {

	public static String EMPTY_VALUE = "";

	public static AutobiographyCreateRequestDto createEmptyAutobiography() {
		return AutobiographyCreateRequestDto.builder()
				.title(EMPTY_VALUE)
				.content(TestAutobiography.DEFAULT_CONTENT)
				.preSignedCoverImageUrl(TestAutobiography.DEFAULT_COVER_IMAGE_URL)
				.interviewQuestions(TestAutobiographyCreateRequestDto.createValidInterviewQuestions())
				.build();
	}

	public static List<InterviewQuestionRequestDto> createValidInterviewQuestions() {
		return List.of(
				InterviewQuestionRequestDto.builder()
						.order(1)
						.questionText("What is your name?")
						.build(),
				InterviewQuestionRequestDto.builder()
						.order(2)
						.questionText("What is your favorite color?")
						.build()
		);
	}
}

package com.lifelibrarians.lifebookshelf.autobiography;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.AutobiographyCreateRequestDto;
import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterCreateRequestDto;
import com.lifelibrarians.lifebookshelf.chapter.domain.Chapter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import com.lifelibrarians.lifebookshelf.auth.jwt.JwtTokenProvider;
import com.lifelibrarians.lifebookshelf.member.domain.Member;

import utils.JsonMatcher;
import utils.PersistHelper;
import utils.test.E2EMvcTest;
import utils.testdouble.autobiography.TestAutobiography;
import utils.testdouble.chapter.TestAutobiographyCreateRequestDto;
import utils.testdouble.chapter.TestChapter;
import utils.testdouble.chapter.TestChapterCreateRequestDto;
import utils.testdouble.chapter.TestChapterStatus;
import utils.testdouble.member.TestMember;

public class AutobiographyControllerTest extends E2EMvcTest {

	private final String URL_PREFIX = "/api/v1/autobiographies";
	private final String EMPTY_VALUE = "";
	private final String BEARER = "Bearer ";
	private final String AUTHORIZE_VALUE = "Authorization";

	private PersistHelper persistHelper;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	private String token;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		super.setup(webApplicationContext);
		this.persistHelper = PersistHelper.start(em);
	}

	@Nested
	@DisplayName("자서전 챕터 목록 생성 (POST /api/v1/autobiographies/chapters)")
	class CreateChapters {

		private final String url = URL_PREFIX + "/chapters";

		@BeforeEach
		void setUp() {
			Member loginMember = persistHelper
					.persistAndReturn(TestMember.asDefaultEntity());
			token = jwtTokenProvider.createMemberAccessToken(
					loginMember.getId()).getTokenValue();
		}

		@Test
		@DisplayName("실패 - 빈 챕터는 생성할 수 없음")
		void 실패_빈_챕터는_생성할_수_없음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createEmptyChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO013"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 16자리 초과의 챕터는 생성할 수 없음")
		void 실패_16자리_초과의_챕터는_생성할_수_없음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createTooManyChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO013"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 챕터 번호 포맷이 유효하지 않은 챕터는 생성할 수 없음")
		void 실패_챕터_번호_포맷이_유효하지_않은_챕터는_생성할_수_없음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createInvalidNumberChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO012"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 챕터 번호가 중복되는 챕터는 생성할 수 없음")
		void 실패_챕터_번호가_중복되는_챕터는_생성할_수_없음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createDuplicatedNumberChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO014"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 챕터 이름이 64자를 초과하는 챕터는 생성할 수 없음")
		void 실패_챕터_이름이_64자를_초과하는_챕터는_생성할_수_없음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createTooLongNameChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO002"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 챕터 번호가 중복되는 서브챕터는 생성할 수 없음")
		void 실패_챕터_번호가_중복되는_서브챕터는_생성할_수_없음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createDuplicatedSubchapterNumberChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO014"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 서브챕터 이름이 64자를 초과하는 서브챕터는 생성할 수 없음")
		void 실패_서브챕터_이름이_64자를_초과하는_서브챕터는_생성할_수_없음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createTooLongSubchapterNameChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO002"))
					.andDo(print());
		}


		@Test
		@DisplayName("실패 - 챕터의 subchapter의 number가 부모 chapter의 number로 시작하지 않음")
		void 실패_챕터의_subchapter의_number가_부모_chapter의_number로_시작하지_않음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapterCreateRequestDto.createInvalidSubchapterNumberChapters())
					.build();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO011"))
					.andDo(print());
		}
	}

	@Test
	@DisplayName("실패 - 이미 챕터가 존재하는 경우, 추가로 챕터를 생성할 수 없음")
	void 실패_이미_챕터가_존재하는_경우_추가로_챕터를_생성할_수_없음() throws Exception {
		// given
		ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
				.chapters(TestChapterCreateRequestDto.createValidChapters())
				.build();
		Member loginMember = persistHelper
				.persistAndReturn(TestMember.asDefaultEntity());
		token = jwtTokenProvider.createMemberAccessToken(
				loginMember.getId()).getTokenValue();
		MockHttpServletRequestBuilder requestBuilder = post(
				URL_PREFIX + "/chapters")
				.header(AUTHORIZE_VALUE, BEARER + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
		mockMvc.perform(requestBuilder);

		// when
		ResultActions resultActions = mockMvc.perform(requestBuilder);

		// then
		JsonMatcher response = JsonMatcher.create();
		resultActions
				.andExpect(status().isConflict())
				.andExpect(response.get("code").isEquals("BIO001"))
				.andDo(print());
	}

	@Test
	@DisplayName("성공 - 유효한 자서전 챕터 목록 생성 요청")
	void 성공_유효한_자서전_챕터_목록_생성_요청() throws Exception {
		// given
		ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
				.chapters(TestChapterCreateRequestDto.createValidChapters())
				.build();
		Member loginMember = persistHelper
				.persistAndReturn(TestMember.asDefaultEntity());
		token = jwtTokenProvider.createMemberAccessToken(
				loginMember.getId()).getTokenValue();

		// when
		MockHttpServletRequestBuilder requestBuilder = post(
				URL_PREFIX + "/chapters")
				.header(AUTHORIZE_VALUE, BEARER + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(chapterCreateRequestDto));
		ResultActions resultActions = mockMvc.perform(requestBuilder);

		// then
		resultActions
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Nested
	@DisplayName("자서전 생성 (POST /api/v1/autobiographies")
	class CreateAutobiography {

		private final String url = URL_PREFIX;
		private Member loginMember;

		@BeforeEach
		void setUp() {
			loginMember = persistHelper
					.persistAndReturn(TestMember.asDefaultEntity());
			token = jwtTokenProvider.createMemberAccessToken(
					loginMember.getId()).getTokenValue();
		}

		@Test
		@DisplayName("실패 - 자서전 제목은 비어있을 수 없음")
		void 실패_자서전_제목은_비어있을_수_없음() throws Exception {
			// given
			List<Chapter> chapters = TestChapter.asDefaultEntities(loginMember);
			persistHelper.persist(chapters);
			List<Chapter> subchapters = Stream.concat(
					TestChapter.asDefaultSubchapterEntities(chapters.get(0), loginMember).stream(),
					TestChapter.asDefaultSubchapterEntities(chapters.get(1), loginMember).stream()
			).collect(Collectors.toList());
			persistHelper.persist(subchapters);
			persistHelper.persist(TestChapterStatus.asDefaultEntity(loginMember, subchapters.get(0)));

			AutobiographyCreateRequestDto autobiographyCreateRequestDto = TestAutobiographyCreateRequestDto
					.createEmptyTitleAutobiography();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(autobiographyCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO005"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 자서전 제목은 64자를 초과할 수 없음")
		void 실패_자서전_제목은_64자를_초과할_수_없음() throws Exception {
			// given
			List<Chapter> chapters = TestChapter.asDefaultEntities(loginMember);
			persistHelper.persist(chapters);
			List<Chapter> subchapters = Stream.concat(
					TestChapter.asDefaultSubchapterEntities(chapters.get(0), loginMember).stream(),
					TestChapter.asDefaultSubchapterEntities(chapters.get(1), loginMember).stream()
			).collect(Collectors.toList());
			persistHelper.persist(subchapters);
			persistHelper.persist(TestChapterStatus.asDefaultEntity(loginMember, subchapters.get(0)));

			AutobiographyCreateRequestDto autobiographyCreateRequestDto = TestAutobiographyCreateRequestDto
					.createTooLongTitleAutobiography();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(autobiographyCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO005"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 자서전 내용은 비어있을 수 없음")
		void 실패_자서전_내용은_비어있을_수_없음() throws Exception {
			// given
			List<Chapter> chapters = TestChapter.asDefaultEntities(loginMember);
			persistHelper.persist(chapters);
			List<Chapter> subchapters = Stream.concat(
					TestChapter.asDefaultSubchapterEntities(chapters.get(0), loginMember).stream(),
					TestChapter.asDefaultSubchapterEntities(chapters.get(1), loginMember).stream()
			).collect(Collectors.toList());
			persistHelper.persist(subchapters);
			persistHelper.persist(TestChapterStatus.asDefaultEntity(loginMember, subchapters.get(0)));

			AutobiographyCreateRequestDto autobiographyCreateRequestDto = TestAutobiographyCreateRequestDto
					.createEmptyContentAutobiography();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(autobiographyCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO006"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 자서전 내용은 30000자를 초과할 수 없음")
		void 실패_자서전_내용은_30000자를_초과할_수_없음() throws Exception {
			// given
			List<Chapter> chapters = TestChapter.asDefaultEntities(loginMember);
			persistHelper.persist(chapters);
			List<Chapter> subchapters = Stream.concat(
					TestChapter.asDefaultSubchapterEntities(chapters.get(0), loginMember).stream(),
					TestChapter.asDefaultSubchapterEntities(chapters.get(1), loginMember).stream()
			).collect(Collectors.toList());
			persistHelper.persist(subchapters);
			persistHelper.persist(TestChapterStatus.asDefaultEntity(loginMember, subchapters.get(0)));

			AutobiographyCreateRequestDto autobiographyCreateRequestDto = TestAutobiographyCreateRequestDto
					.createTooLongContentAutobiography();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(autobiographyCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("BIO006"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 다음 챕터가 존재하지 않는 경우, 자서전을 생성할 수 없음")
		void 실패_다음_챕터가_존재하지_않는_경우_자서전을_생성할_수_없음() throws Exception {
			// given
			List<Chapter> chapters = TestChapter.asDefaultEntities(loginMember);
			persistHelper.persist(chapters);
			List<Chapter> subchapters = new ArrayList<>();
			for (Chapter chapter : chapters) {
				subchapters.addAll(TestChapter.asDefaultSubchapterEntities(chapter, loginMember));
			}
			persistHelper.persist(subchapters);
			subchapters.forEach(subchapter -> persistHelper.persist(
					TestAutobiography.asDefaultEntity(loginMember, subchapter)));
			persistHelper.persist(
					TestChapterStatus.asDefaultEntity(loginMember, subchapters.get(subchapters.size() - 1)));

			AutobiographyCreateRequestDto autobiographyCreateRequestDto = TestAutobiographyCreateRequestDto
					.createValidAutobiography();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(autobiographyCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isNotFound())
					.andExpect(response.get("code").isEquals("BIO015"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 인터뷰 질문 텍스트는 64자를 초과할 수 없음")
		void 실패_인터뷰_질문_텍스트는_64자를_초과할_수_없음() throws Exception {
			// given
			List<Chapter> chapters = TestChapter.asDefaultEntities(loginMember);
			persistHelper.persist(chapters);
			List<Chapter> subchapters = Stream.concat(
					TestChapter.asDefaultSubchapterEntities(chapters.get(0), loginMember).stream(),
					TestChapter.asDefaultSubchapterEntities(chapters.get(1), loginMember).stream()
			).collect(Collectors.toList());
			persistHelper.persist(subchapters);
			persistHelper.persist(TestChapterStatus.asDefaultEntity(loginMember, subchapters.get(0)));

			AutobiographyCreateRequestDto autobiographyCreateRequestDto = TestAutobiographyCreateRequestDto
					.createTooLongInterviewQuestionAutobiography();

			// when
			MockHttpServletRequestBuilder requestBuilder = post(url)
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(autobiographyCreateRequestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
					.andExpect(status().isBadRequest())
					.andExpect(response.get("code").isEquals("INTERVIEW005"))
					.andDo(print());
		}
	}

//3. 자서전 수정 요청
//    1. 자서전 제목 길이 제한 테스트
//    2. 자서전 내용 길이 제한 테스트
//    3. 자서전의 주인이 아닌 경우 테스트
//    4. 존재하지 않는 자서전 테스트
//4. 자서전 삭제 요청
//    1. 자서전의 주인이 아닌 경우 테스트
//    2. 존재하지 않는 자서전 테스트
}

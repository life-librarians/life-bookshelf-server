package com.lifelibrarians.lifebookshelf.autobiography;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lifelibrarians.lifebookshelf.autobiography.dto.request.ChapterCreateRequestDto;
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
import utils.testdouble.chapter.TestChapter;
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
//    1. 챕터의 subchapter의 number가 부모 chapter의 number로 시작하는지 테스트
//    2. 챕터 번호 포맷 유효성 테스트
//    3. 챕터 이름 최대 길이 제한 테스트
//    4. 이미 챕터를 생성한 경우 테스트

		@Test
		@DisplayName("실패 - 챕터의 subchapter의 number가 부모 chapter의 number로 시작하지 않음")
		void 실패_챕터의_subchapter의_number가_부모_chapter의_number로_시작하지_않음() throws Exception {
			// given
			ChapterCreateRequestDto chapterCreateRequestDto = ChapterCreateRequestDto.builder()
					.chapters(TestChapter.createInvalidChapters())
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

//2. 자서전 생성 요청
//    1. 자서전 제목 길이 제한 테스트
//    2. 자서전 내용 길이 제한 테스트
//    3. 챕터의 주인이 아닌 경우 테스트
//    4. 존재하지 않는 챕터 테스트
//    5. 이미 자서전을 가진 챕터 테스트
//    6. 인터뷰 질문 텍스트 길이 제한 테스트
//3. 자서전 수정 요청
//    1. 자서전 제목 길이 제한 테스트
//    2. 자서전 내용 길이 제한 테스트
//    3. 자서전의 주인이 아닌 경우 테스트
//    4. 존재하지 않는 자서전 테스트
//4. 자서전 삭제 요청
//    1. 자서전의 주인이 아닌 경우 테스트
//    2. 존재하지 않는 자서전 테스트
}

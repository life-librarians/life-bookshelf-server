package com.lifelibrarians.lifebookshelf.interview;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lifelibrarians.lifebookshelf.interview.domain.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.lifelibrarians.lifebookshelf.auth.jwt.JwtTokenProvider;
import com.lifelibrarians.lifebookshelf.interview.dto.request.InterviewConversationCreateRequestDto;
import com.lifelibrarians.lifebookshelf.member.domain.Member;

import org.junit.jupiter.api.*;

import utils.JsonMatcher;
import utils.PersistHelper;
import utils.test.E2EMvcTest;
import utils.testdouble.interview.TestInterview;
import utils.testdouble.interview.TestInterviewConversationCreateRequestDto;
import utils.testdouble.member.TestMember;

public class InterviewControllerTest extends E2EMvcTest {

	private final String URL_PREFIX = "/api/v1/interviews";
	private final String EMPTY_VALUE = "";
	private final String BEARER = "Bearer ";
	private final String AUTHORIZE_VALUE = "Authorization";

	private PersistHelper persistHelper;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	private String token;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		super.setup(webApplicationContext);
		this.persistHelper = PersistHelper.start(em);
	}

	@Nested
	@Disabled
	@DisplayName("챗봇과의 대화 내역 전송 요청 (POST /api/v1/interviews/{interviewId}/conversations)")
	class GetInterviewConversations {

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
		@DisplayName("실패 - 인터뷰가 존재하지 않는 경우, 대화 내역을 전송할 수 없음")
		void 실패_인터뷰가_존재하지_않는_경우_대화_내역을_전송할_수_없음() throws Exception {
			// given
			InterviewConversationCreateRequestDto requestDto = TestInterviewConversationCreateRequestDto.createValidInterviewConversationCreateRequestDto();
			long wrongInterviewId = 99999L;

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url + "/" + wrongInterviewId + "/conversations")
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(requestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
//					.andExpect(status().isNotFound())
//					.andExpect(response.get("code").isEquals("INTERVIEW001"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 자신의 인터뷰가 아닌 경우, 대화 내역을 전송할 수 없음")
		void 실패_자신의_인터뷰가_아닌_경우_대화_내역을_전송할_수_없음() throws Exception {
			// given
			InterviewConversationCreateRequestDto requestDto = TestInterviewConversationCreateRequestDto.createValidInterviewConversationCreateRequestDto();
			Member otherMember = persistHelper
					.persistAndReturn(TestMember.asDefaultEntity());
			Interview interview = persistHelper.persistAndReturn(
					TestInterview.asDefaultEntity(otherMember));

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url + "/" + interview.getId() + "/conversations")
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(requestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
//					.andExpect(status().isForbidden())
//					.andExpect(response.get("code").isEquals("INTERVIEW002"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 대화는 20개를 초과하여 전송할 수 없음")
		void 실패_대화는_20개를_초과하여_전송할_수_없음() throws Exception {
			// given
			InterviewConversationCreateRequestDto requestDto = TestInterviewConversationCreateRequestDto.createTooManyConversationsInterviewConversationCreateRequestDto();
			Interview interview = persistHelper.persistAndReturn(
					TestInterview.asDefaultEntity(loginMember));

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url + "/" + interview.getId() + "/conversations")
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(requestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
//					.andExpect(status().isBadRequest())
//					.andExpect(response.get("code").isEquals("INTERVIEW003"))
					.andDo(print());
		}

		@Test
		@DisplayName("실패 - 대화 내용은 512자를 초과하여 전송할 수 없음")
		void 실패_대화_내용은_512자를_초과하여_전송할_수_없음() throws Exception {
			// given
			InterviewConversationCreateRequestDto requestDto = TestInterviewConversationCreateRequestDto.createTooLongContentInterviewConversationCreateRequestDto();
			Interview interview = persistHelper.persistAndReturn(
					TestInterview.asDefaultEntity(loginMember));

			// when
			MockHttpServletRequestBuilder requestBuilder = post(
					url + "/" + interview.getId() + "/conversations")
					.header(AUTHORIZE_VALUE, BEARER + token)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(requestDto));
			ResultActions resultActions = mockMvc.perform(requestBuilder);

			// then
			JsonMatcher response = JsonMatcher.create();
			resultActions
//					.andExpect(status().isBadRequest())
//					.andExpect(response.get("code").isEquals("INTERVIEW004"))
					.andDo(print());
		}

//	성공 - 유효한 대화 내역 전송 요청
	}

	@Nested
	@Disabled
	@DisplayName("현재 진행중인 인터뷰 질문을 다음 질문으로 갱신 요청 (POST /api/v1/interviews/{interviewId}/questions/current-question)")
	class UpdateCurrentQuestion {

//	1. 실패 - 인터뷰가 존재하지 않는 경우, 다음 질문으로 갱신할 수 없음
//	2. 실패 - 자신의 인터뷰가 아닌 경우, 다음 질문으로 갱신할 수 없음
//	3. 실패 - 다음 질문이 존재하지 않는 경우, 다음 질문으로 갱신할 수 없음

//	성공 - 유효한 다음 질문으로 갱신 요청
	}
}


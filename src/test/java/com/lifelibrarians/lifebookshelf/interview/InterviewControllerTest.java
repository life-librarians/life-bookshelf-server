package com.lifelibrarians.lifebookshelf.interview;

import com.lifelibrarians.lifebookshelf.auth.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import utils.PersistHelper;
import utils.test.E2EMvcTest;

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
	@DisplayName("챗봇과의 대화 내역 전송 요청 (POST /api/v1/interviews/{interviewId}/conversations)")
	class GetInterviewConversations {
		
	}
}


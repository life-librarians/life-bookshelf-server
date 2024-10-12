package com.lifelibrarians.lifebookshelf.publication.domain;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.lifelibrarians.lifebookshelf.exception.DomainException;
import com.lifelibrarians.lifebookshelf.log.Logging;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Logging
@Slf4j
@Profile("prod")
public class ProdPublicationManager implements PublicationManager {

	private final AWSLambda awsLambda;

	public void invokeNewPublicationProcessor(Long publicationId) {
		String functionName = "NewPublicationProcessor";

		InvokeRequest invokeRequest = new InvokeRequest()
				.withFunctionName(functionName)
				.withPayload("{\"publicationId\": \"" + publicationId + "\"}");
		InvokeResult invokeResult = null;
		try {
			invokeResult = awsLambda.invoke(invokeRequest);
			String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);
			log.info("Lambda function returned: {}", ans);
		} catch (Exception e) {
			throw new DomainException(HttpStatus.BAD_GATEWAY, "Failed to invoke lambda function");
		}
	}
}
package com.aws.codestar.projecttemplates.handler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.codestar.projecttemplates.GatewayResponse;

/**
 * Handler for requests to Lambda function.
 */
public class HelloWorldHandler implements RequestHandler<Object, Object> {

	@SuppressWarnings("unchecked")
	public Object handleRequest(final Object input, final Context context) {

		context.getLogger().log("Input to the Lambda: " + input + "\n");
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		try {
			if (input instanceof LinkedHashMap) {
				LinkedHashMap<Object, Object> inputRequest = (LinkedHashMap<Object, Object>) input;
				LinkedHashMap<String, String> queryParams = (LinkedHashMap<String, String>) inputRequest
						.get("queryStringParameters");
				context.getLogger().log("queryParams: 	" + queryParams + "\n");

				float loanAmount = Float.parseFloat(queryParams.get("loanAmount"));
				float interestRate = Float.parseFloat(queryParams.get("interestRate"));
				float duration = Float.parseFloat(queryParams.get("duration"));
				context.getLogger().log("Loan Amount:	" + loanAmount + "\n");
				context.getLogger().log("Interest Rate: " + interestRate + "\n");
				context.getLogger().log("Loan Duration: " + duration + "\n");

				float emi = loanAmount / duration;

				StringBuilder response = new StringBuilder();
				response.append("{ \"Easy Monthly Installment: \": \"" + emi + "\"}");
				return new GatewayResponse(response.toString(), headers, HttpStatus.OK.value());
			}
			context.getLogger().log("Incorrect input type: " + input.getClass());
			return new GatewayResponse("{ \"Output\": \"Bad Request!\"}", headers, HttpStatus.BAD_REQUEST.value());
		} catch (Throwable e) {
			context.getLogger().log("Exception Encountered.");
			return new GatewayResponse("{ \"Output\": \"Exception Encountered!\"}", headers,
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
}

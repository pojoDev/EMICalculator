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

	public Object handleRequest(final Object input, final Context context) {

		context.getLogger().log("Input to the Lambda: " + input + "\n");
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		try {
			if (input instanceof LinkedHashMap) {
				@SuppressWarnings("unchecked")
				LinkedHashMap<Object, Object> calculaionParam = (LinkedHashMap<Object, Object>) input;
				context.getLogger().log("calculaionParam: " + calculaionParam.get("queryStringParameters") + "\n");
				context.getLogger().log(
						"calculaionParam getClass: " + calculaionParam.get("queryStringParameters").getClass() + "\n");
				headers.put("Content-Type", "application/json");
				
				StringBuilder response = new StringBuilder();
				response.append("{ \"Output\": \"" + calculaionParam.get("queryStringParameters") + "\"}");
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

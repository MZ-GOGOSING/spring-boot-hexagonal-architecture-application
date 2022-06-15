package me.gogosing.feign.model;

import static feign.Util.valuesOrEmpty;
import static java.lang.String.format;

import feign.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeignResponseUtils {

	public static String getRequestHeader(Response response) {
		StringBuilder builder = new StringBuilder();
		Map<String, Collection<String>> headers = response.request().headers();
		for (String field : headers.keySet()) {
			for (String value : valuesOrEmpty(headers, field)) {
				builder.append(field).append(": ").append(value).append('\n');
			}
		}
		return builder.toString();
	}

	public static String getRequestBody(Response response) {
		if (response.request().body() == null) {
			return "";
		}

		try {
			return new String(response.request().body(), StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			log.error(format("feign request body converting error - response: %s", response), e);
			return "";
		}
	}

	public static String getResponseBody(Response response) {
		if (response.body() == null) {
			return "";
		}

		try (InputStream responseBodyStream = response.body().asInputStream()) {
			return IOUtils.toString(responseBodyStream, StandardCharsets.UTF_8.name());

		} catch (IOException e) {
			log.error(format("feign response body converting error - response: %s", response), e);
			return "";
		}
	}
}

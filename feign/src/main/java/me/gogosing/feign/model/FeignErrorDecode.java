package me.gogosing.feign.model;

import static java.lang.String.format;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gogosing.support.exception.ExternalApiException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * <pre>
 *  요청 결과가 5xx 대 에러라면, 요청을 다시 시도 한다.
 *  단, {@link FeignRetryConfiguration} 을 feign client 에 설정 해야한다.
 * </pre>
 */
@Slf4j
@NoArgsConstructor
public class FeignErrorDecode implements ErrorDecoder {

	/**
	 * <dl>
	 * <dt>5xx 에러가 지속적으로 발생한다면</dt>
	 * <dd>RetryableException 발생하고, 더이상 Retry 를 할 수 없을때 여기서 throw 한 RetryableException 이 응답으로 내려간다.</dd>
	 * </dl>
	 */
	@Override
	public Exception decode(String methodKey, Response response) {
		log.error(
			"{} 요청이 성공하지 못했습니다. status: {} requestUrl: {}, requestHeader: {}, requestBody: {}, responseBody: {}",
			methodKey, response.status(), response.request().url(),
			FeignResponseUtils.getRequestHeader(response), FeignResponseUtils.getRequestBody(response),
			FeignResponseUtils.getResponseBody(
				response));

		if (isRetry(response)) {
			return new RetryableException(
				format("%s 요청이 성공하지 못했습니다. Retry 합니다. - status: %s, headers: %s", methodKey,
					response.status(), response.headers()), response.request().httpMethod(), null);
		}
		return new ExternalApiException(
			format("%s 요청이 성공하지 못했습니다. - cause: %s, headers: %s", methodKey, response.status(),
				response.headers()));
	}

	/**
	 * 4XX, 5XX 에러이면서, GET 요청에 대해서만 retry 한다. 그 이외의 경우에 retry 가 필요하면 별도의 configuration class 에서
	 * ErrorDecoder 를 설정한다.
	 */
	private boolean isRetry(Response response) {
		if (!HttpMethod.GET.toString().equals(response.request().httpMethod().toString())) {
			return false;
		}
		return HttpStatus.valueOf(response.status()).is5xxServerError() || HttpStatus
			.valueOf(response.status()).is4xxClientError();
	}
}

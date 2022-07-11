package me.gogosing.support;

import javax.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpSession;

@EnableAutoConfiguration
public class ServerTestConfig {

	@Bean
	public HttpSession httpSession() {
		return new MockHttpSession();
	}
}

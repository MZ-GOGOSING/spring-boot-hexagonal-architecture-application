package me.gogosing.feign.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class FeignRetryConfiguration {

	@Bean
	public Retryer retryer() {
		return new Retryer.Default();
	}
}

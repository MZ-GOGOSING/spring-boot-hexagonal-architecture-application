package me.gogosing.board.adapter.in.web.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.in.web.bind.LocalDateParamBinder;
import me.gogosing.board.adapter.in.web.bind.LocalDateTimeParamBinder;
import me.gogosing.board.adapter.in.web.bind.LocalTimeParamBinder;
import me.gogosing.board.adapter.in.web.converter.DescriptionCodeJsonConverter;
import me.gogosing.board.adapter.in.web.converter.LocalDateJsonConverter;
import me.gogosing.board.adapter.in.web.converter.LocalDateTimeJsonConverter;
import me.gogosing.support.code.DescriptionCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:/META-INF/resources/")
			.setCacheControl(CacheControl.noCache());

		registry.addResourceHandler("index.html")
			.addResourceLocations("classpath:/META-INF/resources/")
			.setCacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS));
	}

	@Bean
	public ObjectMapper objectMapper() {
		final var objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.registerModule(javaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		return objectMapper;
	}

	@Override
	public void addFormatters(final FormatterRegistry registry) {
		registry.addConverter(new LocalDateParamBinder());
		registry.addConverter(new LocalTimeParamBinder());
		registry.addConverter(new LocalDateTimeParamBinder());
	}

	private SimpleModule javaTimeModule() {
		return new JavaTimeModule()
			.addSerializer(LocalDateTime.class, new LocalDateTimeJsonConverter.Serializer())
			.addDeserializer(LocalDateTime.class, new LocalDateTimeJsonConverter.Deserializer())
			.addSerializer(LocalDate.class, new LocalDateJsonConverter.Serializer())
			.addDeserializer(LocalDate.class, new LocalDateJsonConverter.Deserializer())
			.addSerializer(DescriptionCode.class, new DescriptionCodeJsonConverter.Serializer())
			.addDeserializer(Enum.class, new DescriptionCodeJsonConverter.Deserializer());
	}

	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		final var converter = new MappingJackson2HttpMessageConverter(this.objectMapper());
		converters.add(converter);
		converters.add(new StringHttpMessageConverter());
	}
}

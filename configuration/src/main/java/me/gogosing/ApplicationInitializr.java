package me.gogosing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebAdapterConfig.class, ApplicationConfig.class, BoardPersistenceAdapterConfig.class})
public class ApplicationInitializr {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationInitializr.class, args);
	}
}

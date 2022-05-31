package me.gogosing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebAdapterConfig.class, PersistenceAdapterConfig.class, ApplicationConfig.class})
public class ApplicationInitializr {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationInitializr.class, args);
	}
}

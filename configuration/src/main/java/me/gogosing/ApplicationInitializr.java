package me.gogosing;

import me.gogosing.jpa.board.BoardJpaConfig;
import me.gogosing.jpa.file.FileJpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
	WebAdapterConfig.class,
	ApplicationConfig.class,
	BoardPersistenceAdapterConfig.class,
	FilePersistenceAdapterConfig.class,
	BoardJpaConfig.class,
	FileJpaConfig.class
})
public class ApplicationInitializr {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationInitializr.class, args);
	}
}

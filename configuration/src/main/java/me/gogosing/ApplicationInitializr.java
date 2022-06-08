package me.gogosing;

import me.gogosing.jpa.board.BoardJpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebAdapterConfig.class, ApplicationConfig.class, PersistenceAdapterConfig.class, BoardJpaConfig.class})
public class ApplicationInitializr {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationInitializr.class, args);
	}
}

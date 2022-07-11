package me.gogosing.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.gogosing.ApplicationConfig;
import me.gogosing.BoardPersistenceAdapterConfig;
import me.gogosing.FilePersistenceAdapterConfig;
import me.gogosing.WebAdapterConfig;
import me.gogosing.container.AppContainerConfig;
import me.gogosing.jpa.board.BoardJpaConfig;
import me.gogosing.jpa.file.FileJpaConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {
	ServerTestConfig.class,
	AppContainerConfig.class,
	WebAdapterConfig.class,
	ApplicationConfig.class,
	BoardPersistenceAdapterConfig.class,
	FilePersistenceAdapterConfig.class,
	BoardJpaConfig.class,
	FileJpaConfig.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public @interface IntegrationTestSupport {

}

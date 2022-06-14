package me.gogosing.jpa.file.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileJpaQueryDslConfig {

	@PersistenceContext(unitName = FileJpaDataSourceConfig.FILE_PERSISTENCE_UNIT)
	private EntityManager fileJpaEntityManager;

	@Bean
	public JPAQueryFactory fileJpaQueryFactory() {
		return new JPAQueryFactory(fileJpaEntityManager);
	}
}

package me.gogosing.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoardJpaQueryDslConfig {

	@PersistenceContext(unitName = BoardJpaDataSourceJpaConfig.BOARD_JPA_PERSIST_UNIT)
	private EntityManager boardJpaEntityManager;

	@Bean
	public JPAQueryFactory boardJpaQueryFactory() {
		return new JPAQueryFactory(boardJpaEntityManager);
	}
}

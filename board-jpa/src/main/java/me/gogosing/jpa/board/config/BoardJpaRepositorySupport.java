package me.gogosing.jpa.board.config;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.Assert;

@BoardJpaTransactional(readOnly = true, propagation = Propagation.SUPPORTS)
public abstract class BoardJpaRepositorySupport extends QuerydslRepositorySupport implements
	InitializingBean {

	public BoardJpaRepositorySupport(Class<?> domainClass) {
		super(domainClass);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(getQuerydsl(), "The QueryDsl must not be null.");
	}

	@NotNull
	@Override
	public Querydsl getQuerydsl() {
		return Objects.requireNonNull(super.getQuerydsl());
	}

	@NotNull
	@Override
	public EntityManager getEntityManager() {
		return Objects.requireNonNull(super.getEntityManager());
	}

	@Override
	@PersistenceContext(unitName = BoardJpaDataSourceConfig.BOARD_PERSISTENCE_UNIT)
	public void setEntityManager(@NotNull EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
}

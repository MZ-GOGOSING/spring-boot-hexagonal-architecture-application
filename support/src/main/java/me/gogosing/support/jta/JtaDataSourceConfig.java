package me.gogosing.support.jta;

import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public abstract class JtaDataSourceConfig {

	private static final String TRANSACTION_TYPE = "JTA";

	public static final String JTA_PERSISTENCE_TRANSACTION_MANAGER = "jtaTransactionManager";

	public static final String JTA_ATOMIKOS_TRANSACTION_MANAGER = "atomikosTransactionManager";

	public static final String JTA_USER_TRANSACTION = "userTransaction";

	protected abstract DataSource dataSource(final Properties properties);

	protected abstract LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(final DataSource dataSource);

	protected Map<String, Object> jpaPropertyMap() {
		return Map.of(
			"hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName(),
			"javax.persistence.transactionType", TRANSACTION_TYPE
		);
	}
}

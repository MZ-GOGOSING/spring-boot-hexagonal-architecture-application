package me.gogosing.jta.datasource;

import static me.gogosing.jpa.board.config.BoardJpaDataSourceConfig.BOARD_PERSISTENCE_ENTITY_MANAGER_FACTORY;
import static me.gogosing.jpa.board.config.BoardJpaDataSourceConfig.BOARD_PERSISTENCE_HIBERNATE_PROPERTIES;
import static me.gogosing.jpa.board.config.BoardJpaDataSourceConfig.BOARD_PERSISTENCE_JPA_PROPERTIES;
import static me.gogosing.jpa.board.config.BoardJpaDataSourceConfig.BOARD_PERSISTENCE_PACKAGE;
import static me.gogosing.jpa.board.config.BoardJpaDataSourceConfig.BOARD_PERSISTENCE_UNIT;
import static me.gogosing.support.jta.JtaDataSourceConfig.JTA_PERSISTENCE_TRANSACTION_MANAGER;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import me.gogosing.jta.AtomikosJtaPlatform;
import me.gogosing.support.jta.JtaDataSourceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
@EnableAutoConfiguration(exclude = {
	DataSourceAutoConfiguration.class,
	DataSourceTransactionManagerAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {BOARD_PERSISTENCE_PACKAGE},
	transactionManagerRef = JTA_PERSISTENCE_TRANSACTION_MANAGER,
	entityManagerFactoryRef = BOARD_PERSISTENCE_ENTITY_MANAGER_FACTORY
)
@DependsOn(JTA_PERSISTENCE_TRANSACTION_MANAGER)
@EntityScan(basePackages = {BOARD_PERSISTENCE_PACKAGE})
public class JtaBoardJpaDataSourceConfig extends JtaDataSourceConfig {

	private static final int BOARD_PERSISTENCE_XA_DATA_SOURCE_POOL_SIZE = 5;
	private static final String BOARD_PERSISTENCE_XA_PROPERTIES = "boardPersistenceXaProperties";
	private static final String BOARD_PERSISTENCE_XA_DATA_SOURCE = "boardPersistenceXaDataSource";
	private static final String BOARD_PERSISTENCE_UNIQUE_RESOURCE_NAME = "XA.BOARD.PERSISTENCE";
	private static final String BOARD_XA_DATA_SOURCE_CLASS_NAME = "org.mariadb.jdbc.MariaDbDataSource";

	private final JpaVendorAdapter jpaVendorAdapter;

	@Bean(name = BOARD_PERSISTENCE_XA_PROPERTIES)
	@ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.board.xa-properties")
	public Properties boardPersistenceXaProperties() {
		return new Properties();
	}

	@Bean(name = BOARD_PERSISTENCE_JPA_PROPERTIES)
	@ConfigurationProperties(prefix = "board.persistence.jpa")
	public JpaProperties boardPersistenceJpaProperties() {
		return new JpaProperties();
	}

	@Bean(name = BOARD_PERSISTENCE_HIBERNATE_PROPERTIES)
	@ConfigurationProperties(prefix = "board.persistence.jpa.hibernate")
	public HibernateProperties boardPersistenceHibernateProperties() {
		return new HibernateProperties();
	}

	@Bean(name = BOARD_PERSISTENCE_XA_DATA_SOURCE)
	public DataSource boardPersistenceXaDataSource(
		final @Qualifier(BOARD_PERSISTENCE_XA_PROPERTIES) Properties boardPersistenceXaProperties
	) {
		AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();

		dataSource.setPoolSize(BOARD_PERSISTENCE_XA_DATA_SOURCE_POOL_SIZE);
		dataSource.setUniqueResourceName(BOARD_PERSISTENCE_UNIQUE_RESOURCE_NAME);
		dataSource.setXaDataSourceClassName(BOARD_XA_DATA_SOURCE_CLASS_NAME);
		dataSource.setXaProperties(boardPersistenceXaProperties);

		return dataSource;
	}

	@Bean(name = BOARD_PERSISTENCE_ENTITY_MANAGER_FACTORY)
	@DependsOn(JTA_PERSISTENCE_TRANSACTION_MANAGER)
	public LocalContainerEntityManagerFactoryBean boardPersistenceEntityManagerFactory(
		final @Qualifier(BOARD_PERSISTENCE_XA_DATA_SOURCE) DataSource boardPersistenceXaDataSource
	) {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

		entityManager.setDataSource(boardPersistenceXaDataSource);
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan(BOARD_PERSISTENCE_PACKAGE);
		entityManager.setPersistenceUnitName(BOARD_PERSISTENCE_UNIT);
		entityManager.setJpaPropertyMap(getJpaPropertyMap());

		return entityManager;
	}

	private Map<String, Object> getJpaPropertyMap() {
		Map<String, Object> properties = boardPersistenceHibernateProperties()
			.determineHibernateProperties(boardPersistenceJpaProperties().getProperties(), new HibernateSettings());

		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		return properties;
	}
}

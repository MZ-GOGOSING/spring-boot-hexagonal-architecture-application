package me.gogosing.jpa.board.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import me.gogosing.support.jta.JtaDataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnMissingBean({JtaDataSourceConfig.class})
@EnableAutoConfiguration(exclude = {
	DataSourceAutoConfiguration.class,
	DataSourceTransactionManagerAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {BoardJpaDataSourceConfig.BOARD_PERSISTENCE_PACKAGE},
	transactionManagerRef = BoardJpaDataSourceConfig.BOARD_PERSISTENCE_TRANSACTION_MANAGER,
	entityManagerFactoryRef = BoardJpaDataSourceConfig.BOARD_PERSISTENCE_ENTITY_MANAGER_FACTORY)
@EntityScan(basePackages = {BoardJpaDataSourceConfig.BOARD_PERSISTENCE_PACKAGE})
public class BoardJpaDataSourceConfig {

	public static final String BOARD_PERSISTENCE_ENTITY_MANAGER_FACTORY = "boardPersistenceEntityManagerFactory";
	public static final String BOARD_PERSISTENCE_JPA_PROPERTIES = "boardPersistenceJpaProperties";
	public static final String BOARD_PERSISTENCE_HIBERNATE_PROPERTIES = "boardPersistenceHibernateProperties";
	public static final String BOARD_PERSISTENCE_DATA_SOURCE = "boardPersistenceDataSource";
	public static final String BOARD_PERSISTENCE_TRANSACTION_MANAGER = "boardPersistenceTransactionManager";
	public static final String BOARD_PERSISTENCE_UNIT = "boardPersistence";
	public static final String BOARD_PERSISTENCE_PACKAGE = "me.gogosing.jpa.board";
	public static final String BOARD_PERSISTENCE_JDBC_TEMPLATE = "boardPersistenceJdbcTemplate";

	@Bean(name = BOARD_PERSISTENCE_ENTITY_MANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean boardPersistenceEntityManagerFactory() {
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(),
			boardPersistenceJpaProperties().getProperties(), null)
			.dataSource(boardPersistenceDataSource())
			.properties(boardPersistenceHibernateProperties()
				.determineHibernateProperties(boardPersistenceJpaProperties().getProperties(),
					new HibernateSettings()))
			.persistenceUnit(BOARD_PERSISTENCE_UNIT)
			.packages(BOARD_PERSISTENCE_PACKAGE)
			.build();
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

	@Bean
	@Qualifier(BOARD_PERSISTENCE_DATA_SOURCE)
	@ConfigurationProperties("board.persistence.datasource")
	public DataSource boardPersistenceDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = BOARD_PERSISTENCE_TRANSACTION_MANAGER)
	public PlatformTransactionManager boardPersistenceTransactionManager(
		final @Autowired @Qualifier(BOARD_PERSISTENCE_ENTITY_MANAGER_FACTORY) EntityManagerFactory boardJpaEntityManagerFactory
	) {
		return new JpaTransactionManager(boardJpaEntityManagerFactory);
	}

	@Bean(name = BOARD_PERSISTENCE_JDBC_TEMPLATE)
	public JdbcTemplate boardPersistenceJdbcTemplate(
		final @Qualifier(BOARD_PERSISTENCE_DATA_SOURCE) DataSource dataSource
	) {
		return new JdbcTemplate(dataSource);
	}
}

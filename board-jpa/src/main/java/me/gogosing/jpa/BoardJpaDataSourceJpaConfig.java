package me.gogosing.jpa;

import static me.gogosing.jpa.BoardJpaDataSourceJpaConfig.BOARD_JPA_ENTITY_MANAGER_FACTORY;
import static me.gogosing.jpa.BoardJpaDataSourceJpaConfig.BOARD_JPA_PACKAGE;
import static me.gogosing.jpa.BoardJpaDataSourceJpaConfig.BOARD_JPA_TRANSACTION_MANAGER;

import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {BOARD_JPA_PACKAGE},
	transactionManagerRef = BOARD_JPA_TRANSACTION_MANAGER,
	entityManagerFactoryRef = BOARD_JPA_ENTITY_MANAGER_FACTORY)
public class BoardJpaDataSourceJpaConfig {

	public static final String BOARD_JPA_ENTITY_MANAGER_FACTORY = "boardJpaEntityManagerFactory";
	public static final String BOARD_JPA_PROPERTIES = "boardJpaProperties";
	public static final String BOARD_JPA_HIBERNATE_PROPERTIES = "boardJpaHibernateProperties";
	public static final String BOARD_JPA_DATA_SOURCE = "boardJpaDataSource";
	public static final String BOARD_JPA_TRANSACTION_MANAGER = "boardJpaTransactionManager";
	public static final String BOARD_JPA_PERSIST_UNIT = "boardJpa";
	public static final String BOARD_JPA_PACKAGE = "me.gogosing.jpa";
	public static final String BOARD_JPA_JDBC_TEMPLATE = "boardJpaJdbcTemplate";

	@Bean(name = BOARD_JPA_ENTITY_MANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean boardJpaEntityManagerFactory() {
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(),
			boardJpaProperties().getProperties(), null)
			.dataSource(boardJpaDataSource())
			.properties(boardJpaHibernateProperties()
				.determineHibernateProperties(boardJpaProperties().getProperties(),
					new HibernateSettings()))
			.persistenceUnit(BOARD_JPA_PERSIST_UNIT)
			.packages(BOARD_JPA_PACKAGE)
			.build();
	}

	@Bean(name = BOARD_JPA_PROPERTIES)
	@ConfigurationProperties(prefix = "board.persistence.jpa")
	public JpaProperties boardJpaProperties() {
		return new JpaProperties();
	}

	@Bean(name = BOARD_JPA_HIBERNATE_PROPERTIES)
	@ConfigurationProperties(prefix = "board.persistence.jpa.hibernate")
	public HibernateProperties boardJpaHibernateProperties() {
		return new HibernateProperties();
	}

	@Bean
	@Qualifier(BOARD_JPA_DATA_SOURCE)
	@ConfigurationProperties("board.persistence.datasource")
	public DataSource boardJpaDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = BOARD_JPA_TRANSACTION_MANAGER)
	public PlatformTransactionManager boardJpaTransactionManager(
		@Autowired @Qualifier(BOARD_JPA_ENTITY_MANAGER_FACTORY) EntityManagerFactory boardJpaEntityManagerFactory) {
		return new JpaTransactionManager(boardJpaEntityManagerFactory);
	}

	@Bean(name = BOARD_JPA_JDBC_TEMPLATE)
	public JdbcTemplate boardJpaJdbcTemplate(
		@Qualifier("boardJpaDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}

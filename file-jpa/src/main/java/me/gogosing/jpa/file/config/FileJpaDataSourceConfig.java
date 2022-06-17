package me.gogosing.jpa.file.config;

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
import org.springframework.context.annotation.Primary;
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
	basePackages = {FileJpaDataSourceConfig.FILE_PERSISTENCE_PACKAGE},
	transactionManagerRef = FileJpaDataSourceConfig.FILE_PERSISTENCE_TRANSACTION_MANAGER,
	entityManagerFactoryRef = FileJpaDataSourceConfig.FILE_PERSISTENCE_ENTITY_MANAGER_FACTORY)
@EntityScan(basePackages = {FileJpaDataSourceConfig.FILE_PERSISTENCE_PACKAGE})
public class FileJpaDataSourceConfig {

	public static final String FILE_PERSISTENCE_ENTITY_MANAGER_FACTORY = "filePersistenceEntityManagerFactory";
	public static final String FILE_PERSISTENCE_JPA_PROPERTIES = "filePersistenceJpaProperties";
	public static final String FILE_PERSISTENCE_HIBERNATE_PROPERTIES = "filePersistenceHibernateProperties";
	public static final String FILE_PERSISTENCE_DATA_SOURCE = "filePersistenceDataSource";
	public static final String FILE_PERSISTENCE_TRANSACTION_MANAGER = "filePersistenceTransactionManager";
	public static final String FILE_PERSISTENCE_UNIT = "filePersistence";
	public static final String FILE_PERSISTENCE_PACKAGE = "me.gogosing.jpa.file";
	public static final String FILE_PERSISTENCE_JDBC_TEMPLATE = "filePersistenceJdbcTemplate";

	@Primary
	@Bean(name = FILE_PERSISTENCE_ENTITY_MANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean filePersistenceEntityManagerFactory() {
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(),
			filePersistenceJpaProperties().getProperties(), null)
			.dataSource(filePersistenceDataSource())
			.properties(filePersistenceHibernateProperties()
				.determineHibernateProperties(filePersistenceJpaProperties().getProperties(),
					new HibernateSettings()))
			.persistenceUnit(FILE_PERSISTENCE_UNIT)
			.packages(FILE_PERSISTENCE_PACKAGE)
			.build();
	}

	@Primary
	@Bean(name = FILE_PERSISTENCE_JPA_PROPERTIES)
	@ConfigurationProperties(prefix = "file.persistence.jpa")
	public JpaProperties filePersistenceJpaProperties() {
		return new JpaProperties();
	}

	@Primary
	@Bean(name = FILE_PERSISTENCE_HIBERNATE_PROPERTIES)
	@ConfigurationProperties(prefix = "file.persistence.jpa.hibernate")
	public HibernateProperties filePersistenceHibernateProperties() {
		return new HibernateProperties();
	}

	@Primary
	@Bean
	@Qualifier(FILE_PERSISTENCE_DATA_SOURCE)
	@ConfigurationProperties("file.persistence.datasource")
	public DataSource filePersistenceDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Primary
	@Bean(name = FILE_PERSISTENCE_TRANSACTION_MANAGER)
	public PlatformTransactionManager filePersistenceTransactionManager(
		final @Autowired @Qualifier(FILE_PERSISTENCE_ENTITY_MANAGER_FACTORY) EntityManagerFactory fileJpaEntityManagerFactory
	) {
		return new JpaTransactionManager(fileJpaEntityManagerFactory);
	}

	@Primary
	@Bean(name = FILE_PERSISTENCE_JDBC_TEMPLATE)
	public JdbcTemplate filePersistenceJdbcTemplate(
		final @Qualifier(FILE_PERSISTENCE_DATA_SOURCE) DataSource dataSource
	) {
		return new JdbcTemplate(dataSource);
	}
}

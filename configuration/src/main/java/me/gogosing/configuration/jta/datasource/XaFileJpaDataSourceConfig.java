package me.gogosing.configuration.jta.datasource;

import static me.gogosing.jpa.file.config.FileJpaDataSourceConfig.FILE_PERSISTENCE_ENTITY_MANAGER_FACTORY;
import static me.gogosing.jpa.file.config.FileJpaDataSourceConfig.FILE_PERSISTENCE_HIBERNATE_PROPERTIES;
import static me.gogosing.jpa.file.config.FileJpaDataSourceConfig.FILE_PERSISTENCE_JPA_PROPERTIES;
import static me.gogosing.jpa.file.config.FileJpaDataSourceConfig.FILE_PERSISTENCE_PACKAGE;
import static me.gogosing.jpa.file.config.FileJpaDataSourceConfig.FILE_PERSISTENCE_UNIT;
import static me.gogosing.support.jta.JtaDataSourceConfig.JTA_PERSISTENCE_TRANSACTION_MANAGER;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
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
	basePackages = {FILE_PERSISTENCE_PACKAGE},
	transactionManagerRef = JTA_PERSISTENCE_TRANSACTION_MANAGER,
	entityManagerFactoryRef = FILE_PERSISTENCE_ENTITY_MANAGER_FACTORY
)
@DependsOn(JTA_PERSISTENCE_TRANSACTION_MANAGER)
@EntityScan(basePackages = {FILE_PERSISTENCE_PACKAGE})
public class XaFileJpaDataSourceConfig extends JtaDataSourceConfig {

	private static final int FILE_PERSISTENCE_XA_DATA_SOURCE_POOL_SIZE = 5;
	private static final String FILE_PERSISTENCE_XA_PROPERTIES = "filePersistenceXaProperties";
	private static final String FILE_PERSISTENCE_XA_DATA_SOURCE = "filePersistenceXaDataSource";
	private static final String FILE_PERSISTENCE_UNIQUE_RESOURCE_NAME = "XA.FILE.PERSISTENCE";
	private static final String FILE_XA_DATA_SOURCE_CLASS_NAME = "org.mariadb.jdbc.MariaDbDataSource";

	private final JpaVendorAdapter jpaVendorAdapter;

	@Bean(name = FILE_PERSISTENCE_XA_PROPERTIES)
	@ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.file.xa-properties")
	public Properties filePersistenceXaProperties() {
		return new Properties();
	}

	@Bean(name = FILE_PERSISTENCE_JPA_PROPERTIES)
	@ConfigurationProperties(prefix = "file.persistence.jpa")
	public JpaProperties filePersistenceJpaProperties() {
		return new JpaProperties();
	}

	@Bean(name = FILE_PERSISTENCE_HIBERNATE_PROPERTIES)
	@ConfigurationProperties(prefix = "file.persistence.jpa.hibernate")
	public HibernateProperties filePersistenceHibernateProperties() {
		return new HibernateProperties();
	}

	@Override
	@Bean(name = FILE_PERSISTENCE_XA_DATA_SOURCE)
	@ConfigurationProperties("file.persistence.datasource")
	public DataSource dataSource(
		final @Qualifier(FILE_PERSISTENCE_XA_PROPERTIES) Properties properties
	) {
		final var atomikosDataSourceBean = new AtomikosDataSourceBean();

		atomikosDataSourceBean.setPoolSize(FILE_PERSISTENCE_XA_DATA_SOURCE_POOL_SIZE);
		atomikosDataSourceBean.setUniqueResourceName(FILE_PERSISTENCE_UNIQUE_RESOURCE_NAME);
		atomikosDataSourceBean.setXaDataSourceClassName(FILE_XA_DATA_SOURCE_CLASS_NAME);
		atomikosDataSourceBean.setXaProperties(properties);

		return atomikosDataSourceBean;
	}

	@Override
	@Bean(name = FILE_PERSISTENCE_ENTITY_MANAGER_FACTORY)
	@DependsOn(JTA_PERSISTENCE_TRANSACTION_MANAGER)
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
		final @Qualifier(FILE_PERSISTENCE_XA_DATA_SOURCE) DataSource dataSource
	) {
		final var localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		localContainerEntityManagerFactoryBean.setDataSource(dataSource);
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		localContainerEntityManagerFactoryBean.setPackagesToScan(FILE_PERSISTENCE_PACKAGE);
		localContainerEntityManagerFactoryBean.setPersistenceUnitName(FILE_PERSISTENCE_UNIT);
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaPropertyMap());

		return localContainerEntityManagerFactoryBean;
	}

	@Override
	protected Map<String, Object> jpaPropertyMap() {
		final var propertiesMap = filePersistenceHibernateProperties().determineHibernateProperties(
			filePersistenceJpaProperties().getProperties(),
			new HibernateSettings()
		);

		propertiesMap.putAll(super.jpaPropertyMap());

		return propertiesMap;
	}
}

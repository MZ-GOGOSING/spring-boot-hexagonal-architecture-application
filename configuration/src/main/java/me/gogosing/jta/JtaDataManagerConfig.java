package me.gogosing.jta;

import static me.gogosing.jpa.board.config.BoardJpaDataSourceConfig.BOARD_PERSISTENCE_TRANSACTION_MANAGER;
import static me.gogosing.jpa.file.config.FileJpaDataSourceConfig.FILE_PERSISTENCE_TRANSACTION_MANAGER;
import static me.gogosing.support.jta.JtaDataSourceConfig.JTA_ATOMIKOS_TRANSACTION_MANAGER;
import static me.gogosing.support.jta.JtaDataSourceConfig.JTA_PERSISTENCE_TRANSACTION_MANAGER;
import static me.gogosing.support.jta.JtaDataSourceConfig.JTA_USER_TRANSACTION;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement
public class JtaDataManagerConfig {

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);

		return hibernateJpaVendorAdapter;
	}

	@Bean(name = JTA_USER_TRANSACTION)
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(10000);

		return userTransactionImp;
	}

	@Bean(name = JTA_ATOMIKOS_TRANSACTION_MANAGER, initMethod = "init", destroyMethod = "close")
	public TransactionManager atomikosTransactionManager() {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);

		AtomikosJtaPlatform.transactionManager = userTransactionManager;

		return userTransactionManager;
	}

	@Bean(name = {
		JTA_PERSISTENCE_TRANSACTION_MANAGER,
		BOARD_PERSISTENCE_TRANSACTION_MANAGER,
		FILE_PERSISTENCE_TRANSACTION_MANAGER
	})
	@DependsOn({JTA_USER_TRANSACTION, JTA_ATOMIKOS_TRANSACTION_MANAGER})
	public PlatformTransactionManager transactionManager() throws Throwable {
		UserTransaction userTransaction = userTransaction();

		AtomikosJtaPlatform.transaction = userTransaction;

		return new JtaTransactionManager(userTransaction, atomikosTransactionManager());
	}
}

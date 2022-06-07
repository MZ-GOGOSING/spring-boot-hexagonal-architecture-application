package me.gogosing;

import me.gogosing.board.adapter.out.persistence.config.BoardPersistenceDataSourceJpaConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration(exclude = {
	DataSourceAutoConfiguration.class,
	DataSourceTransactionManagerAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {
	"me.gogosing.board.adapter.out.persistence",
	"me.gogosing.support"
})
@EntityScan(basePackages = {"me.gogosing.board.adapter.out.persistence"})
@Import({BoardPersistenceDataSourceJpaConfig.class})
public class BoardPersistenceAdapterConfig {

}

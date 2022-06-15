package me.gogosing.jpa.board;

import me.gogosing.jpa.board.config.BoardJpaDataSourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
	"me.gogosing.jpa.board",
	"me.gogosing.support"
})
@Import({BoardJpaDataSourceConfig.class})
public class BoardJpaConfig {

}

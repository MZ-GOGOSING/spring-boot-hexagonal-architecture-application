package me.gogosing.jpa.file;

import me.gogosing.jpa.file.config.FileJpaDataSourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
	"me.gogosing.jpa.file",
	"me.gogosing.support"
})
@Import({FileJpaDataSourceConfig.class})
public class FileJpaConfig {

}

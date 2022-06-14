package me.gogosing;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "me.gogosing.*.adapter.out.persistence")
public class FilePersistenceAdapterConfig {

}

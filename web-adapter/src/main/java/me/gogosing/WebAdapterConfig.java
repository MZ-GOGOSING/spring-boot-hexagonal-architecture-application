package me.gogosing;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"me.gogosing.*.adapter.in.web"})
public class WebAdapterConfig {

}

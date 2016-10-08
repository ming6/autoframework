package com.github.ming6.autoframework.bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@EnableAspectJAutoProxy(proxyTargetClass=true)
@PropertySource("classpath:bootstrap.properties")
@ComponentScan(basePackages = "${scan.packages}",
	useDefaultFilters=false,
	includeFilters={
		@Filter(Configuration.class),
		@Filter(Repository.class),
		@Filter(Service.class)
	}
)
public class BootstrapConfig {

//	@Bean
//	public PropertySourcesPlaceholderConfigurer propertyResourceConfigurer(){
//		PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
//		config.setFileEncoding("UTF-8");
//		config.setLocations(new ClassPathResource("bootstrap.properties"));
//		return config;
//	}
}
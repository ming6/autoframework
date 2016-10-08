package com.github.ming6.autoframework.mybatis.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MyBatisConfig implements EnvironmentAware, ApplicationContextAware {
	
	private ApplicationContext applicationContext;

	private String scanPackage;
	private String mapperLocations;
	private String typeAliasesPackage;
	
	@Override
	public void setEnvironment(Environment env) {
		this.scanPackage = env.getProperty("mybatis.scan.package");
		//"classpath*:cn/bidlink/**/mapper/*.xml"
		this.mapperLocations = env.getProperty("mybatis.mapper.locations");
		//"classpath:cn.bidlink.**.model"
//		this.typeAliasesPackage = env.getProperty("mybatis.type.aliases.package");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	SqlSessionFactory sqlSessionFactory(){
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		try {
			factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		factory.setPlugins(new Interceptor[]{
//			new MyBatisUserAuthInterceptor()
		});
		
//		factory.setTypeAliasesPackage(typeAliasesPackage);
//		MybatisDaoLimitInterceptor limitor = new MybatisDaoLimitInterceptor();
//		factory.setPlugins(new Interceptor[]{ limitor });
		try {
			return factory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer scanner = new MapperScannerConfigurer();
		scanner.setBasePackage(scanPackage);
		return scanner;
	}
	
//	@Bean
//	MapperScannerConfigurer mapperScannerConfigurer(){
//		MapperScannerConfigurer config = new MapperScannerConfigurer();
//		config.setSqlSessionFactoryBeanName("sqlSessionFactory");
//		config.setAnnotationClass(Dao.class);
//		config.setBasePackage(scanPackage);
//		return config;
//	}
}
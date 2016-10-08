package com.github.ming6.autoframework.druid.config;

import java.sql.SQLException;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DruidConfig implements EnvironmentAware {
	
	private String type;
	private String url;
	private String username;
	private String password;
	
	@Override
	public void setEnvironment(Environment env) {
		this.type = env.getProperty("druid.type", "mysql");
		this.url = env.getProperty("druid.url");
		this.username = env.getProperty("druid.username");
		this.password = env.getProperty("druid.password");
	}

	@Bean(initMethod="init", destroyMethod="close")
	DruidDataSource druidDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDbType(type);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaxActive(20);
		dataSource.setInitialSize(1);
		dataSource.setMaxWait(60000);
		dataSource.setMinIdle(1);
		dataSource.setTimeBetweenEvictionRunsMillis(3000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		//打开PSCache，并且指定每个连接上PSCache的大小
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		//是否打开removeAbandoned功能，对于建立时间超过removeAbandonedTimeout的连接强制关闭
		dataSource.setRemoveAbandoned(false);
		/**
		 * 打开removeAbandoned功能
		 * */
		//多少秒删除连接，秒为单位，指定连接建立多长时间就需要被强制关闭，如果removeAbandoned为false，这个设置项不再起作用
		dataSource.setRemoveAbandonedTimeout(7200);
		//关闭abanded连接时输出错误日志，指定发生removeAbandoned的时候，是否记录当前线程的堆栈信息到日志中
		dataSource.setLogAbandoned(true);
		//配置监控统计拦截的filters
		try {
			dataSource.setFilters("stat,log4j");
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		List<Filter> filters = new ArrayList<>();
//		StatFilter statFilter = new StatFilter();
//		statFilter.setSlowSqlMillis(10000);
//		statFilter.setLogSlowSql(true);
//		statFilter.setMergeSql(true);
//		filters.add(statFilter);
//		
//		WallFilter wallFilter = new WallFilter();
//		filters.add(wallFilter);
//		
//		Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
//		slf4jLogFilter.setResultSetLogEnabled(true);
//		slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
//		filters.add(slf4jLogFilter);
//		dataSource.setProxyFilters(filters);
		return dataSource;
	}
}
package com.onlytigi.common.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * DataSource 설정
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY, order = 0)
//@MapperScan(basePackages = "com.onlytigi.**.dao", annotationClass = Repository.class)
@ComponentScan(basePackages = {"com.onlytigi.**.dao"}, 
	    	   includeFilters = @Filter(type = FilterType.ANNOTATION, value = {Repository.class}))

public class DataSourceConfig {

	@Value("${jdbc.driverClassName}") 
	private String driverClassName;
	
	@Value("${jdbc.minimumIdle}")
	private int minimumIdle;
	@Value("${jdbc.maximumPoolSize}")
	private int maximumPoolSize;
	@Value("${jdbc.connectionTimeout}")
	private int connectionTimeout;
	@Value("${validationQuery}")
	private String validationQuery;
	@Value("${jdbc.autocommit}")
	private boolean isAutoCommit;	
	
	@Value("${datasource.cachePrepStmts}")
	private String cachePrepStmts;
	@Value("${datasource.prepStmtCacheSize}")
	private String prepStmtCacheSize;
	@Value("${datasource.prepStmtCacheSqlLimit}")
	private String prepStmtCacheSqlLimit;
	@Value("${datasource.useServerPrepStmts}")
	private String useServerPrepStmts;
	
	@Value("${datasource.dbConnUsrNm}")
	private String dbConnUsrNm;
	@Value("${datasource.dbConnPwd}")
	private String dbConnPwd;
	@Value("${datasource.dbConnUrl}")
	private String dbConnUrl;
	
	/**
	 * Hikari data source
	 */
	@Bean(destroyMethod = "shutdown")
	public HikariDataSource hikariDataSource() {
		HikariConfig config = getHikariConfig();
		config.setDriverClassName(driverClassName);
        config.addDataSourceProperty("user", dbConnUsrNm);
        config.addDataSourceProperty("password", dbConnPwd);
        config.setJdbcUrl(dbConnUrl);
	    return new HikariDataSource(config);
	}
	private HikariConfig getHikariConfig() {
		HikariConfig config = new HikariConfig();
        config.setMinimumIdle(minimumIdle);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setConnectionTestQuery(validationQuery);
        config.setConnectionTimeout(connectionTimeout);
        config.setAutoCommit(isAutoCommit);

        config.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
        config.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
        config.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);
        return config;
	}
	
	/**
	 * Data source
	 */
	@Bean
	public DataSource dataSource() {
	    return new LazyConnectionDataSourceProxy(hikariDataSource());
	}
	
	/**
	 * Data source transaction manager
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
		return dataSourceTransactionManager;
	}

	/**
	 * sql session factory
	 * - mybatis-config.xml path
	 * - mapper path
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		sqlSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/**/*.xml"));
		
		// default TypeHandler 등록.
		TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getObject().getConfiguration().getTypeHandlerRegistry();
		typeHandlerRegistry.register(java.sql.Timestamp.class, org.apache.ibatis.type.DateTypeHandler.class);
		typeHandlerRegistry.register(java.sql.Time.class, org.apache.ibatis.type.DateTypeHandler.class);
		typeHandlerRegistry.register(java.sql.Date.class, org.apache.ibatis.type.DateTypeHandler.class);
		
		return sqlSessionFactory.getObject();
	}

	@Bean(destroyMethod = "clearCache")
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sessionTemplate;
	}
}

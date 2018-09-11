package com.gallelloit.springdemo.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * This project has no xml files for configuration purposes. All the configurations are set in Java files.
 * 
 * This Java configuration class enables WebMvc (`@EnableWebMvc`), enables TrasactionManagement
 * (`@EnableTransactionManagement`), sets a package base for component scanning and sets a list of *.properties
 * files for static configuration values.
 * 
 * It defines two DataSource Beans:
 * - "myDataSource": main data source for the main customer model.
 * - "securityDataSource": model used by Spring Security.
 * 
 * Java Configuration File
 * 
 * @author pgallello
 *
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages="com.gallelloit.springdemo")
@PropertySource({
		"classpath:spring-security-mysql.properties",
		"classpath:persistence-mysql.properties"
		})
public class DemoAppConfig implements WebMvcConfigurer{

	// Set a variable to hold the properties
	@Autowired
	private Environment env;
	
	// Logger configuration
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * Define a bean for VewResolver
	 * @return The generated ViewResolver
	 */
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
		
	}
	
	/**
	 * Define a bean for our security datasource.
	 * 
	 * All the values are taken from `persistence-mysql.properties`.
	 * 
	 * @return DataSource bean for main application
	 */
	@Bean
	public DataSource myDataSource() {
		// Create connetion pull
		ComboPooledDataSource myDatasource = new ComboPooledDataSource();
		
		// Set the jdbc driver class
		try {
			myDatasource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		// Log the connection properties
		logger.info(">>>jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info(">>>jdbc.user=" + env.getProperty("jdbc.user"));
		
		// Set the database connection properties
		myDatasource.setJdbcUrl(env.getProperty("jdbc.url"));
		myDatasource.setUser(env.getProperty("jdbc.user"));
		myDatasource.setPassword(env.getProperty("jdbc.password"));
		
		// Set the connection pool properties
		myDatasource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		myDatasource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		myDatasource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		myDatasource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return myDatasource;

	}
	
	private Properties getHibernateProperties() {
		// Set hibernate properties
		Properties props = new Properties();
		
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		return props;
	}
	
	/**
	 * Sets up the session factory
	 * @return The session factory
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// Create session factory
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		
		// Set session factory properties
		localSessionFactoryBean.setDataSource(myDataSource());
		localSessionFactoryBean.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
		
		return localSessionFactoryBean;
	}
	
	/**
	 * 
	 * Sets up the transaction manager. Every method at service layer should be transactional.
	 * 
	 * @param sessionFactory
	 * @return The transaction manager.
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		// Setup transaction manager based on session factory
		
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		
		txManager.setSessionFactory(sessionFactory);
		
		return txManager;
		
	}
	
	/**
	 * Define a bean for our security datasource.
	 * 
	 * All the values are taken from `spring-security-mysql.properties`.
	 * 
	 * @return DataSource bean for security
	 */
	@Bean
	public DataSource securityDataSource() {
		
		// Create connetion pull
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		// Set the jdbc driver class
		try {
			securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		// Log the connection properties
		logger.info(">>>security.jdbc.url=" + env.getProperty("security.jdbc.url"));
		logger.info(">>>security.jdbc.user=" + env.getProperty("security.jdbc.user"));
		
		// Set the database connection properties
		securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
		securityDataSource.setUser(env.getProperty("security.jdbc.user"));
		securityDataSource.setPassword(env.getProperty("security.jdbc.password"));
		
		// Set the connection pool properties
		securityDataSource.setInitialPoolSize(getIntProperty("security.connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("security.connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("security.connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("security.connection.pool.maxIdleTime"));
		
		return securityDataSource;
	}
	
	// Helper method to read environment property and convert it to int
	private int getIntProperty(String propertyName) {
		
		String propertyValue = env.getProperty(propertyName);
		
		// Convert it to int
		int intPropertyValue = Integer.parseInt(propertyValue);
		
		return intPropertyValue;
		
	}
	
	/**
	 * 
	 * Maps the static resources
	 * 
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/");

	}
	
	
	
}

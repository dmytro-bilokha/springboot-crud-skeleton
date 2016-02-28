package bilokhado.phonebook.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.config.BatchWriting;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
// @EnableJpaRepositories("com.example.rest.service")
public class PersistanceJPAConfig extends JpaBaseConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "bilokhado.phonebook.entity" });
		em.setPersistenceUnitName("phonebookSQLPU");
		em.setJpaVendorAdapter(createJpaVendorAdapter());
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/phonebook");
		dataSource.setUsername("www");
		dataSource.setPassword("world-wide-wlak");
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new EclipseLinkJpaVendorAdapter();
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
		properties.setProperty("javax.persistence.schema-generation.create-source", "script");
		properties.setProperty("javax.persistence.schema-generation.drop-source", "script");
		properties.setProperty("javax.persistence.schema-generation.create-script-source", "META-INF/create.sql");
		properties.setProperty("javax.persistence.schema-generation.drop-script-source", "META-INF/drop.sql");
		properties.setProperty("javax.persistence.sql-load-script-source", "META-INF/load.sql");
		properties.setProperty("eclipselink.ddl-generation", "create-drop");
		return properties;
	}

	@Override
	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> vendorProperties = new HashMap<>();
		vendorProperties.put(PersistenceUnitProperties.WEAVING, detectWeavingMode());
		return vendorProperties;
	}

	private String detectWeavingMode() {
		return InstrumentationLoadTimeWeaver.isInstrumentationAvailable() ? "true" : "static";
	}
}

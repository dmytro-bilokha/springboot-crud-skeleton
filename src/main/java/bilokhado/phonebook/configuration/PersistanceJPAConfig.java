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
		// em.setPersistenceUnitName("phonebookSQLPU");
		em.setJpaVendorAdapter(createJpaVendorAdapter());
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
	
	@Override
	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> vendorProperties = new HashMap<>();
		vendorProperties.put(PersistenceUnitProperties.WEAVING, detectWeavingMode());
		vendorProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_DATABASE_ACTION, "drop-and-create");
		vendorProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_SOURCE, "script");
		vendorProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_DROP_SOURCE, "script");
		vendorProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE, "META-INF/create.sql");
		vendorProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_DROP_SCRIPT_SOURCE, "META-INF/drop.sql");
		vendorProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_SQL_LOAD_SCRIPT_SOURCE, "META-INF/load.sql");
		return vendorProperties;
	}

	private String detectWeavingMode() {
		return InstrumentationLoadTimeWeaver.isInstrumentationAvailable() ? "true" : "static";
	}
}

package bilokhado.phonebook.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan("bilokhado.phonebook.entity")
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "SQL")
public class PersistenceJpaSqlConfig extends JpaBaseConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(PersistenceJpaSqlConfig.class);

	@Bean
	@Override
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factoryBuilder) {
		logger.info("WE ARE IN THE entityManagerFactory!");
		/*LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
         em.setDataSource(dataSource());
         em.setPackagesToScan(getPackagesToScan());
         em.setJpaVendorAdapter(createJpaVendorAdapter());
         em.setPersistenceUnitName("SQL");
         em.setJpaPropertyMap(getVendorProperties());
         em.setMappingResources(new String[] { "META-INF/sqluser-orm.xml" });
         return em;*/
		Map<String, Object> vendorProperties = getVendorProperties();
		customizeVendorProperties(vendorProperties);
		LocalContainerEntityManagerFactoryBean emf = factoryBuilder.dataSource(dataSource()).packages(getPackagesToScan()).persistenceUnit("SQL")
				.properties(vendorProperties).jta(isJta()).build();
		//emf.setMappingResources(new String[] { "META-INF/sqluser-orm.xml" });
		return emf;
	}

	@Bean
	public DataSource dataSource() {
		logger.info("WE ARE IN THE dataSource!");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/phonebook");
		dataSource.setUsername("www");
		dataSource.setPassword("world-wide-wlak");
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		logger.info("WE ARE IN THE transactionManager!");
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		logger.info("WE ARE IN THE createJpaVendorAdapter!");
		return new EclipseLinkJpaVendorAdapter();
	}

	@Override
	protected String[] getPackagesToScan() {
		logger.info("WE ARE IN THE getPackagesToScan!");
		return new String[] { "bilokhado.entity.sql" };
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		logger.info("WE ARE IN THE exceptionTranslation!");
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Override
	protected Map<String, Object> getVendorProperties() {
		logger.info("WE ARE IN THE getVendorProperties!");
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
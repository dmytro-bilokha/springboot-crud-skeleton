package bilokhado.phonebook.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import bilokhado.phonebook.configuration.properties.SqlStorageProperties;

@Configuration
//@ComponentScan("bilokhado.phonebook.configuration.properties")
@EnableTransactionManagement
@EntityScan({"bilokhado.phonebook.entity"})
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "SQL")
public class PersistenceJpaSqlConfig extends JpaBaseConfiguration {

	@Autowired
	SqlStorageProperties properties;

	@Value("${db.sql.driver}")
	private String driver;
	
	@Value("${db.type}")
	private String type;
	
	@Bean
	@Override
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factoryBuilder) {
		Map<String, Object> vendorProperties = getVendorProperties();
		customizeVendorProperties(vendorProperties);
		LocalContainerEntityManagerFactoryBean emf = factoryBuilder.dataSource(dataSource())
				.packages(getPackagesToScan()).persistenceUnit("SQL").properties(vendorProperties).jta(isJta()).build();
		return emf;
	}

	@Bean
	public DataSource dataSource() {
		System.out.println("DATASOURCE called");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		System.out.println(driver);
		System.out.println(type);
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		System.out.println(properties.getUrl());
		dataSource.setUrl("jdbc:mysql://localhost:3306/phonebook");
		System.out.println(properties.getUser());
		dataSource.setUsername("www");
		System.out.println(properties.getPassword());
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

	@Override
	protected String[] getPackagesToScan() {
		return new String[] { "bilokhado.phonebook.entity" };
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Override
	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> vendorProperties = new HashMap<>();
		// Disable JPA BeanValidation, we validate by controller
		vendorProperties.put(PersistenceUnitProperties.VALIDATION_MODE, "NONE");
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
	
	@PostConstruct
    public void postConstruct() {
        System.out.print("SampleApplication started: " + driver +" " + type);
    }
}

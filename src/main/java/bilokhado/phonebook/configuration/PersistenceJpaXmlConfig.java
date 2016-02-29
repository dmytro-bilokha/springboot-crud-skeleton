package bilokhado.phonebook.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "XML")
public class PersistenceJpaXmlConfig extends JpaBaseConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPackagesToScan(new String[] { "bilokhado.phonebook.entity.xml" });
		em.setJpaVendorAdapter(createJpaVendorAdapter());
		em.setPersistenceUnitName("XML");
		return em;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
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
		return new String[] { "bilokhado.entity.xml" };
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
		vendorProperties.put(PersistenceUnitProperties.TARGET_DATABASE,
				"org.eclipse.persistence.eis.adapters.xmlfile.XMLFilePlatform");
		vendorProperties.put("eclipselink.nosql.connection-spec",
				"org.eclipse.persistence.eis.adapters.xmlfile.XMLFileEISConnectionSpec");
		vendorProperties.put("eclipselink.nosql.property.directory", "/usr/home/dimon/temp/");
		vendorProperties.put("eclipselink.logging.level", "FINEST");
		return vendorProperties;
	}

	private String detectWeavingMode() {
		return InstrumentationLoadTimeWeaver.isInstrumentationAvailable() ? "true" : "static";
	}
}

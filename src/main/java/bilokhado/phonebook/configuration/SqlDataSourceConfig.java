package bilokhado.phonebook.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import bilokhado.phonebook.configuration.properties.SqlStorageProperties;

@Configuration
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "SQL")
public class SqlDataSourceConfig {

	@Autowired
	SqlStorageProperties properties;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(properties.getDriver());
		dataSource.setUrl(properties.getUrl());
		dataSource.setUsername(properties.getUser());
		dataSource.setPassword(properties.getPassword());
		return dataSource;
	}

}

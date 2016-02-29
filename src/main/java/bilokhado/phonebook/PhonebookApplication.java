package bilokhado.phonebook;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
public class PhonebookApplication {

	private static final String CONFIG_FILE_NAME_PROPERTY = "lordi.conf";
	private static final String DEFAULT_CONFIG_FILE = "defaultconfig.properties";

	private static final Logger logger = LoggerFactory.getLogger(PhonebookApplication.class);

	public static void main(String[] args) throws IOException {
		SpringApplication springApplication = new SpringApplication(new Object[] { PhonebookApplication.class });
		List<Resource> propertiesResources = new LinkedList<>();
		propertiesResources.add(new ClassPathResource(DEFAULT_CONFIG_FILE));
		String externalPropertiesFileName = System.getProperty(CONFIG_FILE_NAME_PROPERTY);
		if (externalPropertiesFileName != null)
			propertiesResources.add(new PathResource(externalPropertiesFileName));
		else
			logger.info("External properties file name is null, ignoring external properties");
		loadProperties(springApplication, propertiesResources);
		springApplication.run(args);
		logger.info("Application started!");
	}

	private static int loadProperties(SpringApplication application, List<Resource> resources) {
		int loadCounter = 0;
		for (Resource resource : resources) {
			if (resource.exists() && resource.isReadable()) {
				try {
					Properties propertiesToLoad = new Properties();
					propertiesToLoad.load(resource.getInputStream());
					application.setDefaultProperties(propertiesToLoad);
					logger.info("Loaded properties file {}", resource.getDescription());
					loadCounter++;
				} catch (IOException ex) {
					logger.error("Unable to load properties file {}", resource.getDescription(), ex);
				}
			} else {
				logger.warn("Resource {} is not readable, ignoring it's properties", resource.getDescription());
			}
		}
		return loadCounter;
	}
}

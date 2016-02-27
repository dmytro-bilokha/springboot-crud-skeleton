package bilokhado.phonebook;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class PhonebookApplication {

	private static final String CONFIG_FILE_NAME_PROPERTY = "lordi.conf";

	private static final Logger logger = LoggerFactory.getLogger(PhonebookApplication.class);
	
	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) throws IOException {
		SpringApplication springApplication = new SpringApplication(new Object[] { PhonebookApplication.class });
		String propertiesFileName = System.getProperty(CONFIG_FILE_NAME_PROPERTY);
		logger.info("Using properties file: {}", propertiesFileName);
		Resource externalPropertiesResource = new PathResource(propertiesFileName);
		Properties externalProperties = new Properties();
		externalProperties.load(externalPropertiesResource.getInputStream());
		springApplication.setDefaultProperties(externalProperties);
		logger.info("Properties setted");
		springApplication.run(args);
		logger.info("Application started!");
	}
}

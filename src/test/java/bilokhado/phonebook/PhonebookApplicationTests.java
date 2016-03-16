package bilokhado.phonebook;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import bilokhado.phonebook.configuration.properties.XmlStorageProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PhonebookApplication.class)
@WebAppConfiguration
@TestPropertySource("classpath:/testxml.properties")
public class PhonebookApplicationTests {

	public static final String TEST_CONFIG_FILENAME = "/usr/home/dimon/temp/testusers.xml";
	public static final boolean TEST_XML_FORMATTED = true;
	
	@Autowired
	XmlStorageProperties properties;
	
	@Test
	public void xmlStoragePropertiesTest() {
		assertThat(properties.isFormatted(), equalTo(TEST_XML_FORMATTED));
		assertThat(properties.getFileName(), equalTo(TEST_CONFIG_FILENAME));
	}

}

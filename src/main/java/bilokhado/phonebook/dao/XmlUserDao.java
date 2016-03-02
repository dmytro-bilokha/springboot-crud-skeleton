package bilokhado.phonebook.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import bilokhado.phonebook.configuration.properties.XmlStorageProperties;
import bilokhado.phonebook.entity.SqlUser;
import bilokhado.phonebook.entity.User;
import bilokhado.phonebook.entity.XmlUser;

@Component
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "XML")
public class XmlUserDao implements UserDao {

	@Autowired
	XmlStorageProperties properties;

	private XmlUsersWrapper xmlUsersWrapper;

	private static final Logger logger = LoggerFactory.getLogger(XmlUserDao.class);

	@Override
	public User findByUserName(String username) {
		return xmlUsersWrapper.users.get(username);
	}
	
	@Override
	public User createNewUser() {
		return new XmlUser();
	}
	
	@Override 
	public void persistUser(User user) {
		if (!(user instanceof XmlUser))
			throw new IllegalArgumentException("Method supports only XmlUser type, but got object " + user);
		XmlUser sqlUser = (XmlUser) user;
		xmlUsersWrapper.users.put(sqlUser.getLogin(), sqlUser);
	}

	@PostConstruct
	public void init() {
		String inputFileName = properties.getFileName();
		if (inputFileName != null) {
			File inputFile = new File(inputFileName);
			if (inputFile.isFile() && inputFile.canRead())
				try {
					JAXBContext context = JAXBContext.newInstance(XmlUsersWrapper.class);
					Unmarshaller unmarshaller = context.createUnmarshaller();
					xmlUsersWrapper = (XmlUsersWrapper) unmarshaller.unmarshal(inputFile);
				} catch (JAXBException ex) {
					logger.error("Failed to unmarshallize users from XML file {}", inputFileName, ex);
					throw new RuntimeException("Failed to unmarshallize users from XML file " + inputFileName, ex);
				}
			else {
				logger.warn("File {} does not exist or is not readable. Will work with empty data, try to save on exit",
						inputFileName);
				xmlUsersWrapper = new XmlUsersWrapper();
			}
		} else {
			logger.error("XML storage file name is not set."
					+ "All data will be stored in memory only and lost on application exit!");
			xmlUsersWrapper = new XmlUsersWrapper();
		}
		/*
		 * XmlUser mockUser = new XmlUser(); mockUser.setLogin("user");
		 * mockUser.setPasswordHash(
		 * "$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y");
		 * mockUser.setFullName("Sql user"); users.put("user", mockUser);
		 */

	}

	@PreDestroy
	public void save() {
		String outputFileName = properties.getFileName();
		if (outputFileName != null)
			try (FileOutputStream outStream = new FileOutputStream(outputFileName);
					BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream);) {
				JAXBContext context = JAXBContext.newInstance(XmlUsersWrapper.class);
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.marshal(xmlUsersWrapper, bufOutStream);
			} catch (JAXBException ex) {
				logger.error("Failed to marshallize users to XML file {}", outputFileName, ex);
			} catch (FileNotFoundException ex) {
				logger.error("Unable to find XML file {}", outputFileName, ex);
			} catch (IOException ex) {
				logger.error("Unable to write XML file {}. Input/Output exception thrown", outputFileName, ex);
			}
		else
			logger.error("XML storage file name is not set. All data will be lost!");
	}

}

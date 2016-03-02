package bilokhado.phonebook.dao;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import bilokhado.phonebook.entity.User;
import bilokhado.phonebook.entity.XmlUser;

//@Repository
@Component
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "XML")
@XmlRootElement(name = "userrepository")
public class XmlUserDao implements UserDao {

	@XmlElement(name = "users")
	private ConcurrentMap<String, XmlUser> users = new ConcurrentHashMap<>();

	@Override
	public User findByUserName(String username) {
		return users.get(username);
	}

	@PostConstruct
	public void init() {
		XmlUser mockUser = new XmlUser();
		mockUser.setLogin("user");
		mockUser.setPasswordHash("$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y");
		mockUser.setFullName("Sql user");
		users.put("user", mockUser);
	}

	@PreDestroy
	public void save() {
		try {
			JAXBContext context = JAXBContext.newInstance(this.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(this, System.out);
		} catch (JAXBException ex) {
			System.out.println("OOOPS!");
			ex.printStackTrace();
		}
	}

}

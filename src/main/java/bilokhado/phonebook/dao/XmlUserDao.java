package bilokhado.phonebook.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import bilokhado.phonebook.entity.User;
import bilokhado.phonebook.entity.XmlUser;

@Repository
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "XML")
public class XmlUserDao implements UserDao {

	@Override
	public User findByUserName(String username) {
		User mockUser = new XmlUser();
		mockUser.setLogin("user");
		mockUser.setPasswordHash("$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y");
		mockUser.setFullName("Sql user");
		return mockUser;
	}

}

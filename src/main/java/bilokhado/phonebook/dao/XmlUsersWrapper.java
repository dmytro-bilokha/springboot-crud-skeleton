package bilokhado.phonebook.dao;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import bilokhado.phonebook.entity.User;

@XmlRootElement(name = "userrepository")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlUsersWrapper {
	@XmlElement(name = "users")
	ConcurrentMap<String, User> users;

	XmlUsersWrapper() {
		users = new ConcurrentHashMap<>();
	}
}

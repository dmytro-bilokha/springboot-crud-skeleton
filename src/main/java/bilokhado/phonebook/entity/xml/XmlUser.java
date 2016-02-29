package bilokhado.phonebook.entity.xml;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

import org.eclipse.persistence.nosql.annotations.NoSql;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import bilokhado.phonebook.entity.User;

@NoSql
@Entity
@NamedQuery(name = "User.findByLogin", query = "SELECT user FROM XmlUser user WHERE user.login = :login")
public class XmlUser implements Serializable, User {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private String login;
	private String passwordHash;
	private String fullName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public int hashCode() {
		int result = (login == null) ? 0 : login.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.getLogin() != null)
				return false;
		} else if (!login.equals(other.getLogin()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + "]";
	}

}

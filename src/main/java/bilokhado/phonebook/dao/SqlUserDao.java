package bilokhado.phonebook.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bilokhado.phonebook.entity.SqlUser;
import bilokhado.phonebook.entity.User;

@Repository
@ConditionalOnProperty(prefix = "db", value = "type", havingValue = "SQL")
public class SqlUserDao implements UserDao {

	@PersistenceContext
	EntityManager em;

	@Transactional(readOnly = true)
	@Override
	public User findByUserName(String login) {
		TypedQuery<User> userQuery = em.createNamedQuery("User.findByLogin", User.class);
		List<User> users = userQuery.setParameter("login", login).getResultList();
		return users.isEmpty() ? null : users.get(0);
	}
	
	@Override
	public User createNewUser() {
		return new SqlUser();
	}

	@Override 
	public void persistUser(User user) {
		if (!(user instanceof SqlUser))
			throw new IllegalArgumentException("Method supports only SqlUser type!");
		SqlUser sqlUser = (SqlUser) user;
		em.persist(sqlUser);
	}
}

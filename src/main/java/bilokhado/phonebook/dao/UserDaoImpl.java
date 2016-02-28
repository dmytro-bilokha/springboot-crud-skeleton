package bilokhado.phonebook.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import bilokhado.phonebook.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	EntityManager em;

	@Override
	public User findByUserName(String login) {
		TypedQuery<User> userQuery = em.createNamedQuery("User.findByLogin", User.class);
		User user = userQuery.setParameter("login", login).getSingleResult();
		return user;
	}

}

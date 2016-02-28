package bilokhado.phonebook.dao;

import bilokhado.phonebook.entity.User;

public interface UserDao {

	User findByUserName(String username);

}
